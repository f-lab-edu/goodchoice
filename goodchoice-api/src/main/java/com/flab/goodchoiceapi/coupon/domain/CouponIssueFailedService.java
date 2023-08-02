package com.flab.goodchoiceapi.coupon.domain;

import com.flab.goodchoicecoupon.application.CouponIssueFailedCommand;
import com.flab.goodchoicecoupon.domain.CouponIssueFailed;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CouponIssueFailedService {

    private final CouponIssueFailedCommand couponIssueFailedCommand;

    public CouponIssueFailedService(CouponIssueFailedCommand couponIssueFailedCommand) {
        this.couponIssueFailedCommand = couponIssueFailedCommand;
    }

    public void createCouponIssueFailed(Long memberId, UUID couponToken) {
        log.error("failed to create memberId : {}, couponIssue : {}", memberId, couponToken);

        CouponIssueFailed couponIssueFailedEvent = CouponIssueFailed.builder()
                .memberId(memberId)
                .couponToken(couponToken)
                .restoredYn(false)
                .build();
        couponIssueFailedCommand.save(couponIssueFailedEvent);
    }
}
