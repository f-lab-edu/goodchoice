package com.flab.goodchoiceapi.coupon.domain;

import com.flab.goodchoicecoupon.application.CouponCommand;
import com.flab.goodchoicecoupon.application.CouponIssueChecker;
import com.flab.goodchoicecoupon.application.CouponIssueCommand;
import com.flab.goodchoicecoupon.application.CouponQuery;
import com.flab.goodchoicecoupon.domain.Coupon;
import com.flab.goodchoicecoupon.domain.CouponIssue;
import com.flab.goodchoicecoupon.exception.CouponException;
import com.flab.goodchoiceredis.common.aop.LimitedCountLock;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@Transactional
@Service
public class CouponIssueService {

    private final CouponQuery couponQuery;
    private final CouponCommand couponCommand;
    private final CouponIssueCommand couponIssueCommand;
    private final CouponIssueChecker couponIssueExistChecker;

    public CouponIssueService(CouponQuery couponQuery, CouponCommand couponCommand, CouponIssueCommand couponIssueCommand, CouponIssueChecker couponIssueExistChecker) {
        this.couponQuery = couponQuery;
        this.couponCommand = couponCommand;
        this.couponIssueCommand = couponIssueCommand;
        this.couponIssueExistChecker = couponIssueExistChecker;
    }

    public boolean couponIssue(final Long memberId, final UUID couponToken) {
        couponIssueExistChecker.duplicateCouponIssueCheck(memberId, couponToken);

        Coupon coupon = couponQuery.getCouponInfoLock(couponToken);
        coupon.useCoupon();

        try {
            createCouponIssue(memberId, coupon);
            couponCommand.modify(coupon);

            return true;
        } catch (CouponException e) {
            return false;
        }
    }

    @LimitedCountLock(key = "key", waitTime = 20L)
    public boolean couponIssueRedissonAop(final Long memberId, final UUID key) {
        couponIssueExistChecker.duplicateCouponIssueCheck(memberId, key);

        Coupon coupon = couponQuery.getCoupon(key);
        coupon.useCoupon();

        try {
            createCouponIssue(memberId, coupon);
            modify(coupon);

            return true;
        } catch (CouponException e) {
            return false;

        }
    }

    public void modify(Coupon coupon) {
        couponCommand.modify(coupon);
    }

    private CouponIssue createCouponIssue(Long memberId, Coupon coupon) {
        CouponIssue couponIssue = new CouponIssue(UUID.randomUUID(), memberId, coupon, false);
        return couponIssueCommand.save(couponIssue);
    }
}
