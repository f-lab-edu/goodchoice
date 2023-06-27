package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponIssueExistCheck;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponIssueRepository;

import java.util.UUID;

public class FakeCouponIssueExistCheck implements CouponIssueExistCheck {

    private final CouponIssueRepository couponIssueRepository;

    public FakeCouponIssueExistCheck(CouponIssueRepository couponIssueRepository) {
        this.couponIssueRepository = couponIssueRepository;
    }

    @Override
    public boolean couponIssueCheck(Long memberId, UUID couponToken) {
        return couponIssueRepository.existsByMemberEntityIdAndCouponEntity_CouponToken(memberId, couponToken);
    }
}
