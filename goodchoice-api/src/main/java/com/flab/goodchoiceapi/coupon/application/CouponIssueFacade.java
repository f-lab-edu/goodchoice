package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoiceapi.coupon.domain.CouponIssueFailedService;
import com.flab.goodchoiceapi.coupon.domain.CouponIssueService;
import com.flab.goodchoicecoupon.domain.Coupon;
import com.flab.goodchoicemember.application.MemberRetrievalService;
import com.flab.goodchoiceredis.common.aop.LimitedCountLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Service
public class CouponIssueFacade {

    private final MemberRetrievalService memberRetrievalService;
    private final CouponService couponService;
    private final CouponIssueService couponIssueService;
    private final CouponIssueRetrievalService couponIssueRetrievalService;
    private final CouponIssueFailedService couponIssueFailedService;

    public CouponIssueFacade(MemberRetrievalService memberRetrievalService, CouponService couponService, CouponIssueService couponIssueService, CouponIssueRetrievalService couponIssueRetrievalService, CouponIssueFailedService couponIssueFailedService) {
        this.memberRetrievalService = memberRetrievalService;
        this.couponService = couponService;
        this.couponIssueService = couponIssueService;
        this.couponIssueRetrievalService = couponIssueRetrievalService;
        this.couponIssueFailedService = couponIssueFailedService;
    }

    @Transactional
    public boolean couponIssue(final Long memberId, final UUID couponToken) {
        memberRetrievalService.getMember(memberId);
        couponIssueRetrievalService.duplicateCouponIssueCheck(memberId, couponToken);
        Coupon coupon = couponIssueService.getCouponInfoLock(couponToken);

        boolean result = couponIssueService.couponIssue(memberId, couponToken, coupon);

        if (!result) {
            couponIssueFailedService.createCouponIssueFailed(memberId, coupon.getId());
            couponIssueService.modify(coupon);
        }

        return result;
    }

    @Transactional
    @LimitedCountLock(key = "key", waitTime = 20L)
    public boolean couponIssueRedissonAop(final Long memberId, final UUID key) {
        memberRetrievalService.getMember(memberId);
        couponIssueRetrievalService.duplicateCouponIssueCheck(memberId, key);

        boolean result = couponIssueService.couponIssueRedissonAop(memberId, key);

        if (!result) {
            Coupon coupon = couponService.getCoupon(key);
            couponIssueFailedService.createCouponIssueFailed(memberId, coupon.getId());
            couponIssueService.modify(coupon);
        }

        return result;
    }
}
