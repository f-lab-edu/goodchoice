package com.flab.goodchoiceapi.coupon.domain;

import com.flab.goodchoicecoupon.application.CouponIssueFailedCommand;
import com.flab.goodchoicecoupon.domain.CouponIssueFailed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional
@Service
public class CouponIssueFailedService {

    private final CouponIssueFailedCommand couponIssueFailedCommand;

    public CouponIssueFailedService(CouponIssueFailedCommand couponIssueFailedCommand) {
        this.couponIssueFailedCommand = couponIssueFailedCommand;
    }

    public void createCouponIssueFailed(Long memberId, Long couponId) {
        log.error("failed to create memberId : {}, couponId : {}", memberId, couponId);

        CouponIssueFailed couponIssueFailedEvent = CouponIssueFailed.builder()
                .memberId(memberId)
                .couponId(couponId)
                .restoredYn(false)
                .build();
        couponIssueFailedCommand.save(couponIssueFailedEvent);
    }
}
