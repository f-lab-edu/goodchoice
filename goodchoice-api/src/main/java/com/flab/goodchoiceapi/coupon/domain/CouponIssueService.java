package com.flab.goodchoiceapi.coupon.domain;

import com.flab.goodchoicecoupon.application.CouponCommand;
import com.flab.goodchoicecoupon.application.CouponIssueCommand;
import com.flab.goodchoicecoupon.application.CouponQuery;
import com.flab.goodchoicecoupon.domain.Coupon;
import com.flab.goodchoicecoupon.domain.CouponIssue;
import com.flab.goodchoicecoupon.exception.CouponException;
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

    public CouponIssueService(CouponQuery couponQuery, CouponCommand couponCommand, CouponIssueCommand couponIssueCommand) {
        this.couponQuery = couponQuery;
        this.couponCommand = couponCommand;
        this.couponIssueCommand = couponIssueCommand;
    }

    public boolean couponIssue(final Long memberId, final UUID couponToken, final Coupon coupon) {
        coupon.useCoupon();

        try {
            createCouponIssue(memberId, coupon);
            modify(coupon);

            return true;
        } catch (CouponException e) {
            return false;
        }
    }

    public boolean couponIssueRedissonAop(final Long memberId, final UUID key) {
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

    public Coupon getCouponInfoLock(UUID couponToken) {
        return couponQuery.getCouponInfoLock(couponToken);
    }

    private CouponIssue createCouponIssue(Long memberId, Coupon coupon) {
        CouponIssue couponIssue = new CouponIssue(UUID.randomUUID(), memberId, coupon, false);
        return couponIssueCommand.save(couponIssue);
    }
}
