package com.ssafy.i10a709be.domain.product.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ssafy.i10a709be.common.entity.Files;
import com.ssafy.i10a709be.common.exception.NoAuthorizationException;
import com.ssafy.i10a709be.common.repository.FileRepository;
import com.ssafy.i10a709be.domain.community.dto.ChatRoomCreateDto;
import com.ssafy.i10a709be.domain.community.enums.ChatRoomStatus;
import com.ssafy.i10a709be.domain.community.service.ChatService;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import com.ssafy.i10a709be.domain.notification.dto.NotificationCreateRequestDto;
import com.ssafy.i10a709be.domain.notification.enums.NotificationType;
import com.ssafy.i10a709be.domain.notification.repository.NotificationRepository;
import com.ssafy.i10a709be.domain.notification.service.KafkaNotificationProducerService;
import com.ssafy.i10a709be.domain.product.dto.ProductSaveRequestDto;
import com.ssafy.i10a709be.domain.product.entity.*;
import com.ssafy.i10a709be.domain.product.enums.ProductStatus;
import com.ssafy.i10a709be.domain.product.repository.*;
import jakarta.persistence.EntityNotFoundException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final UnitRepository unitRepository;
    private final MemberRepository memberRepository;
    private final PartTypeRepository partTypeRepository;
    private final CategoryRepository categoryRepository;
    private final UnitImagesRepository unitImagesRepository;
    private final ChatService chatService;
    private final FileRepository fileRepository;
    private final KafkaNotificationProducerService notificationProducerService;

    //TODO 1차 개발 끝나면 해당 로직 세분화를 시켜서 재사용성을 높히자.
    //단일 파츠 및 유닟 및 상품 생성
    @Override
    @Transactional
    public Product saveProduct(String memberId, ProductSaveRequestDto request) {
        log.debug( memberId );
        log.info("save Product member Id = " + memberId);
        Optional<Member> member = memberRepository.findById(memberId);
        Optional<Category> category = categoryRepository.findById(request.getUnit().getCategoryId());

        if (member.isPresent() && category.isPresent()) {
            Product product = Product.builder()
                    .member(member.get())
                    .title(request.getProductTitle())
                    .status(ProductStatus.ON_SELL)
                    .maxAge(request.getUnit().getAge())
                    .totalPrice(request.getUnit().getPrice())
                    .isOnly(true)
                    .build();

            log.info("product 생성 완료={} ", product.toString());
            Unit unit = Unit.builder()
                    .member(member.get())
                    .product(product)
                    .originalProductId(product.getProductId())
                    .category(category.get())
                    .isCombinable(request.getUnit().getIsCombinable())
                    .unitDescription(request.getUnit().getUnitDescription())
                    .price(request.getUnit().getPrice())
                    .age(request.getUnit().getAge())
                    .status(request.getUnit().getStatus())
                    .isConfirmed(true)
                    .build();

            log.info("unit 생성 완료={} ", unit.toString());
            for (Long imageId : request.getUnit().getImages()) {
                Files file = fileRepository.findById(imageId).orElseThrow(() -> new IllegalArgumentException("이미지가 존재하지 않습니다."));
                UnitImages unitImages = UnitImages.builder()
                        .files(file)
                        .unit(unit)
                        .build();
                unitImagesRepository.save(unitImages);

                unit.getUnitImages().add(unitImagesRepository.findById(unitImages.getId()).orElseThrow(() -> new IllegalArgumentException("유닛에 이미지가 존재하지 않습니다.")));
                file.getUnitImages().add(unitImagesRepository.findById(unitImages.getId()).orElseThrow(() -> new IllegalArgumentException("파일에 이미지가 존재하지 않습니다.")));
            }
            log.info("unit image 생성 완료={} ", unit.getUnitImages().toString());
            product.getUnits().add(unit);

            for (Long partTypeId : request.getUnit().getPartTypeIds()) {
                Optional<PartType> partType = partTypeRepository.findById(partTypeId);
                log.info("partType = {}" ,partType.toString());
                if (partType.isPresent()) {

                    Part part = Part.builder()
                            .unit(unit)
                            .partType(partType.get())
                            .build();

                    unit.getParts().add(part);
                } else {
                    throw new IllegalArgumentException();
                }
            }
            log.info("part 생성 완료={} ", unit.getParts().toString());

            // 한 명이 모든 파츠를 묶어서 올리는 경우 조합 제품
            if (unit.getParts().size() == 3){
                product.updateIsOnly(false);
            }

            productRepository.save(product);
            //새로 생성하기에
            unit.setOriginalProductId(product.getProductId());

            if (!request.getTargetUnits().isEmpty()) {
                product.updateStatus(ProductStatus.PENDING);
                composeUnits(product, unit, request.getTargetUnits());
            }
            return product;
        }

        throw new IllegalArgumentException();
    }

    //합의시 일때 실행되는 로직
    @Override
    @Transactional
    public void composeUnits(Product product, Unit unit, List<Long> targets) {

        // 본인이 애초에 합의를 열었기에 true에서 바꿀 필요가 없다.
        unit.updateProduct(product);
        product.getUnits().add(unit);

        //채팅방 생성을 위한 member list 생성
        List<Member> memberList = new ArrayList<>();
        memberList.add( unit.getMember());
        for (Long targetUnitId : targets){
            unitRepository.findById(targetUnitId).ifPresent(
                    targetUnit -> {
                        targetUnit.getProduct().softDeleted(true);//targetUnit의 원래 product의 isdeleted는 true로 바껴야함
                        targetUnit.setIsConfirmed( false ); // 나머지 친구들은 거절
                        targetUnit.updateProduct(product);
                        product.updateTotalPrice(targetUnit.getPrice()); // 04-02 totalprice 수정
                        memberList.add( targetUnit.getMember() );
                        product.getUnits().add(targetUnit);
                    }
            );
        }

        ChatRoomCreateDto dto = new ChatRoomCreateDto( memberList, unit.getMember().getMemberId(), product.getTitle() +"상품 합의 채팅방입니다.", 1 + targets.size(), ChatRoomStatus.active, product.getProductId());
        chatService.createChatRoom( dto );
        NotificationCreateRequestDto notificationCreateRequestDto = NotificationCreateRequestDto
                .builder()
                .topicSubject("product")
                .members((ArrayList<String>) memberList.stream().map((member) -> {
                    return member.getMemberId();
                }).collect(Collectors.toList()))
                .content(product.getTitle() + " 상품이 생성되었습니다. 어서 합의해주세요!")
                .isRead(false)
                .notificationType(NotificationType.confirm)
                .productId(product.getProductId())
                .build();
        notificationProducerService.sendNotificationToKafkaTopic(notificationCreateRequestDto);
    }

    @Transactional
    @Override
    public Page<Product> findAllProduct(Pageable pageable, Long productId, Boolean isCombined, String nickname, String memberId, Long categoryId, String productStatus, Integer startPrice, Integer endPrice, Integer maxAge, String keyword, Boolean isOnly) {
        return productRepository.findProductsByDynamicQuery(pageable, productId, isCombined, nickname, memberId, categoryId, productStatus, startPrice, endPrice, maxAge, keyword, isOnly);
    }

    @Transactional
    @Override
    public Product findProduct(Long productId) {
        return productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));
    }

    @Override
    @Transactional
    public String modifyProduct(Long productId, String productTitle) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new IllegalArgumentException("해당 상품이 존재하지 않습니다."));

        product.modifyTitle(productTitle);

        return product.getTitle();
    }

    @Override
    @Transactional
    public void deleteProduct(String memberId, Long productId) {
        Product product = productRepository.deleteProductById(productId, memberId).orElseThrow(() -> new IllegalArgumentException("해당상품이 존재하지않습니다."));
        List<Unit> units = product.getUnits();
        product.softDeleted(Boolean.TRUE);

        // 단일 판매일 경우와 묶음 판매일 경우 로직 상이
        if (units.size() == 1) {
            units.get(0).softDeleted(Boolean.TRUE);
        } else {
            for (Unit unit : units) {
                Product originalProduct = productRepository.findById(unit.getOriginalProductId())
                        .orElseThrow(() -> new IllegalArgumentException("문제가 계속될 시 wntjrdbs@gmail.com으로 연락주세요."));
                unit.updateProduct(originalProduct);
            }
        }
    }
    //Compose 생성 로직
    @Transactional
    @Override
    public Long createAfterCompose(String memberId, Long unitId, ProductSaveRequestDto productSaveRequestDto) {
        Member member = memberRepository.findById( memberId ).orElseThrow( () -> new NoAuthorizationException("해당 사용자가 없습니다.", this));
        Unit unit = unitRepository.findById( unitId ).orElseThrow( () -> new EntityNotFoundException("찾으시는 유닛이 없습니다"));
        Product product = productRepository.findProductAndUnitsByProductId( unit.getOriginalProductId() ).orElseThrow( () -> new IllegalArgumentException("잘못된 상품 정보 요청입니다."));
        product.softDeleted( true );

        // 권한 체크
        if (member.getMemberId() != product.getMember().getMemberId())
            throw new NoAuthorizationException("잘못된 요청입니다.", this);
        // 재사용
        Product newProduct = Product.builder()
                .member(member)
                .title(productSaveRequestDto.getProductTitle())
                .status(ProductStatus.PENDING)
                .maxAge(productSaveRequestDto.getUnit().getAge())
                .totalPrice(productSaveRequestDto.getUnit().getPrice())
                .isOnly(false)
                .build();

        newProduct.getUnits().add(unit);
        unit.updateProduct(newProduct);

        Product saved = productRepository.save(newProduct);
        
//        for (Long targetUnitId : productSaveRequestDto.getTargetUnits()){
//            unitRepository.findById(targetUnitId).ifPresent(
//                    targetUnit -> {
//                        targetUnit.getProduct().softDeleted(true);//targetUnit의 원래 product의 isdeleted는 true로 바껴야함
//                        targetUnit.setIsConfirmed( false ); // 나머지 친구들은 거절
//                        targetUnit.updateProduct(newProduct);
//                        newProduct.getUnits().add(targetUnit);
//                    }
//            );
//        }
        composeUnits( saved, unit, productSaveRequestDto.getTargetUnits());

        return saved.getProductId();
    }
}