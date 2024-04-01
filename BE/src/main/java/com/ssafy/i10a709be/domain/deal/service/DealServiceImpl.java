package com.ssafy.i10a709be.domain.deal.service;


import com.ssafy.i10a709be.common.exception.CustomJpaPersistenceException;
import com.ssafy.i10a709be.common.exception.InternalServerException;
import com.ssafy.i10a709be.common.exception.NoAuthorizationException;
import com.ssafy.i10a709be.common.exception.OriginalProductNotFoundException;
import com.ssafy.i10a709be.domain.community.dto.ChatRoomCreateDto;
import com.ssafy.i10a709be.domain.community.enums.ChatRoomStatus;
import com.ssafy.i10a709be.domain.community.service.ChatService;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import com.ssafy.i10a709be.domain.notification.dto.NotificationCreateRequestDto;
import com.ssafy.i10a709be.domain.notification.enums.NotificationType;
import com.ssafy.i10a709be.domain.notification.service.KafkaNotificationProducerService;
import com.ssafy.i10a709be.domain.notification.service.NotificationService;
import com.ssafy.i10a709be.domain.product.entity.Product;
import com.ssafy.i10a709be.domain.product.entity.Unit;
import com.ssafy.i10a709be.domain.product.enums.ProductStatus;
import com.ssafy.i10a709be.domain.product.repository.ProductRepository;
import com.ssafy.i10a709be.domain.product.repository.UnitRepository;
import jakarta.persistence.PersistenceException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DealServiceImpl implements DealService {
    private final ChatService chatService;
    private final MemberRepository memberRepository;
    private final ProductRepository productRepository;
    private final UnitRepository unitRepository;
    private final KafkaNotificationProducerService notificationProducerService;

    //합의 승인과정 + 전부 승인시 product 개시
    @Override
    public void approveConfirm(Long productId, String memberId) {
        Unit findUnit = unitRepository.findUnitByProduct_ProductIdAndMember_MemberId(productId, memberId);
        findUnit.setIsConfirmed(true);

//    이렇게 돌면 결국 N + 1 문제가 발생한다.
//        for( Unit unit : findUnit.getProduct().getUnits() ){
//            if()
//        }
        Product p = productRepository.findProductAndUnitsByProductId(productId).orElseThrow(() -> new OriginalProductNotFoundException("찾으시는 상품이 없습니다.", this));
        for (Unit unit : p.getUnits()) {
            if (!unit.getIsConfirmed()) {
                return;
            }
        }
        p.updateStatus(ProductStatus.ON_SELL);
    }


    @Override
    public void rejectConfirm(Long productId, String memberId) {
        Product p = productRepository.findProductAndUnitsByProductId(productId).orElseThrow(() -> new OriginalProductNotFoundException("찾으시는 상품이 없습니다.", this));

        boolean flag = false;
        for (Unit unit : p.getUnits()) {
            if (unit.getMember().getMemberId().equals(memberId)) flag = true;
        }
        if (!flag) throw new NoAuthorizationException("권한이 없는 사용자입니다.", this);

        p.softDeleted(true); // 거절 뜨면 이 친구는 삭제되어야 한다.
        // 각 유닛은 원래 자기 자리로 돌아간다.
        try {
            for (Unit unit : p.getUnits()) {
                Product targetProduct = productRepository.findById(unit.getOriginalProductId()).orElseThrow(() -> new OriginalProductNotFoundException("해당 유닛의 해당하는 원래 상품을 찾을 수 없습니다.", this));
                targetProduct.softDeleted(false); // 삭제를 취소하고
                targetProduct.updateStatus(ProductStatus.ON_SELL); //판매로 바꿔주고
                unit.updateProduct(targetProduct); //원래 product로 변경
            }
        } catch (PersistenceException e) {
            throw new CustomJpaPersistenceException("Unit 복구간 Persistence 에러가 발생하였습니다.", this);
        } catch (RuntimeException e) {
            throw new InternalServerException("예외 처리에 실패한 로직이 발생하였습니다", this);
        }
    }

    /*
        구매자만 진행할 수 있어야하는 구매 시작, 완료 로직
     */
    @Override
    public void startTrading(Long productId, String memberId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new IllegalArgumentException("해당 프로덕트는 존재하지 않습니다.")
        );
        Member member = memberRepository.findById(memberId).orElseThrow(() ->
                new IllegalArgumentException("해당 멤버는 존재하지 않습니다.")
        );

        //채팅방 생성
        List<Member> members = new ArrayList<>();
        List<Unit> units = product.getUnits();
        for (Unit unit : units) {
            members.add(unit.getMember());
        }
        members.add(member);

        chatService.createChatRoom(ChatRoomCreateDto.builder()
                .joinMembers(members)
                .memberId(memberId)
                .title(product.getTitle() + " 구매 채팅방")
                .capability(members.size())
                .status(ChatRoomStatus.active)
                .productId(productId)
                .build()
        );

        product.updateDealInfo(memberId, ProductStatus.ON_CONTRACT);
        NotificationCreateRequestDto notificationCreateRequestDto = NotificationCreateRequestDto
                .builder()
                .topicSubject("product")
                .members((ArrayList<String>) members.stream().map((memberElem) -> {
                    return memberElem.getMemberId();
                }).collect(Collectors.toList()))
                .content(product.getTitle() + " 상품의 구매 요청이 있습니다.")
                .isRead(false)
                .notificationType(NotificationType.confirm)
                .productId(product.getProductId())
                .build();
        notificationProducerService.sendNotificationToKafkaTopic(notificationCreateRequestDto);
    }

    @Override
    public void transactionComplete(Long productId, String memberId) {
        Product product = productRepository.findById(productId).orElseThrow(() ->
                new IllegalArgumentException("해당 프로덕트는 존재하지 않습니다.")
        );

        if (product.getConsumerId().equals(memberId)) {
            product.updateStatus(ProductStatus.COMPLETE);
            NotificationCreateRequestDto notificationCreateRequestDto = NotificationCreateRequestDto
                    .builder()
                    .topicSubject("product")
                    .members((ArrayList<String>) product.getUnits().stream().map((unit) -> {
                        return unit.getMember().getMemberId();
                    }).collect(Collectors.toList()))
                    .content(product.getTitle() + " 상품의 거래가 완료되었습니다.")
                    .isRead(false)
                    .notificationType(NotificationType.confirm)
                    .productId(product.getProductId())
                    .build();
            notificationProducerService.sendNotificationToKafkaTopic(notificationCreateRequestDto);
        } else{
            throw new IllegalArgumentException("구매자가 아니라면 상품 구매를 완료할 수 없습니다.");
        }
    }
}
