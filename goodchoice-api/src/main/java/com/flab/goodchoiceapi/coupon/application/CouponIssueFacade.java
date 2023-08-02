package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoiceapi.coupon.domain.CouponIssueFailedService;
import com.flab.goodchoiceapi.coupon.domain.CouponIssueService;
import com.flab.goodchoicecoupon.domain.Coupon;
import com.flab.goodchoicemember.application.MemberRetrievalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
public class CouponIssueFacade {

    private final MemberRetrievalService memberRetrievalService;
    private final CouponService couponService;
    private final CouponIssueService couponIssueService;
    private final CouponIssueFailedService couponIssueFailedService;

    public CouponIssueFacade(MemberRetrievalService memberRetrievalService, CouponService couponService, CouponIssueService couponIssueService, CouponIssueFailedService couponIssueFailedService) {
        this.memberRetrievalService = memberRetrievalService;
        this.couponService = couponService;
        this.couponIssueService = couponIssueService;
        this.couponIssueFailedService = couponIssueFailedService;
    }

    public boolean couponIssue(final Long memberId, final UUID couponToken) {
        memberRetrievalService.getMember(memberId);

        boolean result = couponIssueService.couponIssue(memberId, couponToken);

        if (!result) {
            couponIssueFailedService.createCouponIssueFailed(memberId, couponToken);
            Coupon coupon = couponService.getCoupon(couponToken);
            couponIssueService.modify(coupon);
        }

        return result;
    }
}
