package com.flab.goodchoicecoupon.infrastructure;

import com.flab.goodchoicecoupon.application.CouponIssueChecker;
import com.flab.goodchoicecoupon.exception.CouponError;
import com.flab.goodchoicecoupon.exception.CouponException;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueRepository;

import java.util.UUID;

public class FakeCouponIssueExistChecker implements CouponIssueChecker {

    private final CouponIssueRepository couponIssueRepository;

    public FakeCouponIssueExistChecker(CouponIssueRepository couponIssueRepository) {
        this.couponIssueRepository = couponIssueRepository;
    }

    @Override
    public void duplicateCouponIssueCheck(Long memberId, UUID couponToken) {
        boolean existsCoupon = couponIssueRepository.existsByMemberIdAndCouponEntity_CouponToken(memberId, couponToken);
        if (existsCoupon) {
            throw new CouponException(CouponError.NOT_DUPLICATION_COUPON);
        }
    }
}
