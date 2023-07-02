package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponIssueChecker;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponIssueRepository;

import java.util.UUID;

public class FakeCouponIssueExistChecker implements CouponIssueChecker {

    private final CouponIssueRepository couponIssueRepository;

    public FakeCouponIssueExistChecker(CouponIssueRepository couponIssueRepository) {
        this.couponIssueRepository = couponIssueRepository;
    }

    @Override
    public void duplicateCouponIssueCheck(Long memberId, UUID couponToken) {
        couponIssueRepository.existsByMemberEntityIdAndCouponEntity_CouponToken(memberId, couponToken);
    }
}
