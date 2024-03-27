package com.ssafy.i10a709be.domain.deal.service;


import com.ssafy.i10a709be.common.exception.CustomJpaPersistenceException;
import com.ssafy.i10a709be.common.exception.InternalServerException;
import com.ssafy.i10a709be.common.exception.NoAuthorizationException;
import com.ssafy.i10a709be.common.exception.OriginalProductNotFoundException;
import com.ssafy.i10a709be.domain.member.entity.Member;
import com.ssafy.i10a709be.domain.member.repository.MemberRepository;
import com.ssafy.i10a709be.domain.product.entity.Product;
import com.ssafy.i10a709be.domain.product.entity.Unit;
import com.ssafy.i10a709be.domain.product.enums.ProductStatus;
import com.ssafy.i10a709be.domain.product.repository.ProductRepository;
import com.ssafy.i10a709be.domain.product.repository.UnitRepository;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class DealServiceImpl implements DealService{
    private final ProductRepository productRepository;
    private final UnitRepository unitRepository;

    //합의 승인과정 + 전부 승인시 product 개시
    @Override
    public void approveConfirm(Long productId, String memberId) {
        Unit findUnit = unitRepository.findUnitByProduct_ProductIdAndMember_MemberId( productId, memberId );
        findUnit.setIsConfirmed( true );

//    이렇게 돌면 결국 N + 1 문제가 발생한다.
//        for( Unit unit : findUnit.getProduct().getUnits() ){
//            if()
//        }
        Product p = productRepository.findProductAndUnitsByProductId( productId ).orElseThrow( () -> new OriginalProductNotFoundException("찾으시는 상품이 없습니다.",this));
        for( Unit unit: p.getUnits() ) {
            if( !unit.getIsConfirmed() ){
                return;
            }
        }
        p.updateStatus( ProductStatus.ON_SELL );
    }



    @Override
    public void rejectConfirm(Long productId, String memberId) {
        Product p = productRepository.findProductAndUnitsByProductId( productId ).orElseThrow( () -> new OriginalProductNotFoundException("찾으시는 상품이 없습니다.",this));

        boolean flag = false;
        for( Unit unit: p.getUnits() ){
            if( unit.getMember().getMemberId().equals( memberId )) flag = true;
        }
        if(!flag) throw new NoAuthorizationException("권한이 없는 사용자입니다.", this );

        p.softDeleted( true ); // 거절 뜨면 이 친구는 삭제되어야 한다.
        // 각 유닛은 원래 자기 자리로 돌아간다.
        try{
            for( Unit unit : p.getUnits() ){
                Product targetProduct = productRepository.findById( unit.getOriginalProductId() ).orElseThrow( () -> new OriginalProductNotFoundException( "해당 유닛의 해당하는 원래 상품을 찾을 수 없습니다.", this ));
                targetProduct.softDeleted( false ); // 삭제를 취소하고
                targetProduct.updateStatus( ProductStatus.ON_SELL ); //판매로 바꿔주고
                unit.updateProduct( targetProduct ); //원래 product로 변경
            }
        }catch( PersistenceException e ){
            throw new CustomJpaPersistenceException( "Unit 복구간 Persistence 에러가 발생하였습니다.", this );
        }catch( RuntimeException e ){
            throw new InternalServerException( "예외 처리에 실패한 로직이 발생하였습니다", this );
        }


    }
}
