package com.ssafy.i10a709be.domain.deal.service;


public interface DealService {

    void approveConfirm(Long productId, String memberId);

    void rejectConfirm(Long productId, String memberId);

    void startTrading(Long productId, String memberId);

    void transactionComplete(Long productId, String memberId);
}
