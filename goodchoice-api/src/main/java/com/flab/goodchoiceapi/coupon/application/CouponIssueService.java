package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.application.*;
import com.flab.goodchoicecoupon.domain.Coupon;
import com.flab.goodchoicecoupon.domain.CouponIssue;
import com.flab.goodchoicecoupon.domain.CouponIssueFailedEvent;
import com.flab.goodchoicecoupon.exception.CouponException;
import com.flab.goodchoicemember.application.MemberQuery;
import com.flab.goodchoicemember.domain.model.Member;
import com.flab.goodchoiceredis.common.aop.LimitedCountLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Transactional
@Service
public class CouponIssueService {

    private final MemberQuery memberQuery;
    private final CouponQuery couponQuery;
    private final CouponCommand couponCommand;
    private final CouponIssueCommand couponIssueCommand;
    private final CouponIssueChecker couponIssueExistChecker;
    private final CouponIssueFailedEventCommand couponIssueFailedEventCommand;

    public CouponIssueService(MemberQuery memberQuery, CouponQuery couponQuery, CouponCommand couponCommand, CouponIssueCommand couponIssueCommand,
                              CouponIssueChecker couponIssueExistChecker, CouponIssueFailedEventCommand couponIssueFailedEventCommand) {
        this.memberQuery = memberQuery;
        this.couponQuery = couponQuery;
        this.couponCommand = couponCommand;
        this.couponIssueCommand = couponIssueCommand;
        this.couponIssueExistChecker = couponIssueExistChecker;
        this.couponIssueFailedEventCommand = couponIssueFailedEventCommand;
    }

    public boolean couponIssue(final Long memberId, final UUID couponToken) {
        getMember(memberId);

        couponIssueExistChecker.duplicateCouponIssueCheck(memberId, couponToken);

        Coupon coupon = couponQuery.getCouponInfoLock(couponToken);
        coupon.useCoupon();

        try {
            createCouponIssue(memberId, coupon);
            couponCommand.modify(coupon);

            return true;
        } catch (CouponException e) {
            log.error("failed to create memberId : {}, couponIssue : {}", memberId, couponToken);
            createCouponIssueFailedEvent(memberId, couponToken);
            couponCommand.modify(coupon);
        }

        return false;
    }

    @LimitedCountLock(key = "key", waitTime = 20L)
    public boolean couponIssueRedissonAop(final Long memberId, final UUID key) {
        getMember(memberId);

        couponIssueExistChecker.duplicateCouponIssueCheck(memberId, key);

        Coupon coupon = couponQuery.getCoupon(key);
        coupon.useCoupon();

        try {
            createCouponIssue(memberId, coupon);
            couponCommand.modify(coupon);

            return true;
        } catch (CouponException e) {
            log.error("failed to create memberId : {}, couponIssue : {}", memberId, key);
            createCouponIssueFailedEvent(memberId, key);
            couponCommand.modify(coupon);
        }

        return false;
    }

    private CouponIssue createCouponIssue(Long memberId, Coupon coupon) {
        CouponIssue couponIssue = new CouponIssue(UUID.randomUUID(), memberId, coupon, false);
        return couponIssueCommand.save(couponIssue);
    }

    private Member getMember(Long memberId) {
        return memberQuery.getMember(memberId);
    }

    private void createCouponIssueFailedEvent(Long memberId, UUID couponToken) {
        CouponIssueFailedEvent couponIssueFailedEvent = new CouponIssueFailedEvent(memberId, couponToken, false);
        couponIssueFailedEventCommand.save(couponIssueFailedEvent);
    }
}
