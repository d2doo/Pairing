package com.ssafy.i10a709be.domain.deal.controller.restcontroller;


import com.ssafy.i10a709be.domain.deal.service.DealService;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.A;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deal")
@RequiredArgsConstructor
public class DealRestController {
    private final DealService dealService;


    //합의 승인
    @PostMapping("/confirm/{productId}")
    public ResponseEntity<Void> approveConfirm(@PathVariable Long productId, @AuthenticationPrincipal String memberId ){
        dealService.approveConfirm( productId, memberId );
        return ResponseEntity.ok().build();
    }

    //합의 거절
    @DeleteMapping("/confirm/{productId}")
    public ResponseEntity<Void> rejectConfirm(@PathVariable Long productId, @AuthenticationPrincipal String memberId ){
        dealService.rejectConfirm( productId, memberId );
        return ResponseEntity.ok().build();
    }

    /*
        구매 신청 & 완료
        구매자만이 해당 Request를 보낼 수 있어야합니다.
     */
    @PatchMapping("/buy/{productId}")
    public ResponseEntity<Void> startTrading(@PathVariable Long productId, @AuthenticationPrincipal String memberId){
        dealService.startTrading(productId, memberId);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/complete/{productId}")
    public ResponseEntity<Void> transactionComplete(@PathVariable Long productId, @AuthenticationPrincipal String memberId){
        dealService.transactionComplete(productId, memberId);
        return ResponseEntity.ok().build();
    }
}
