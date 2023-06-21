package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponPublishExistCheck;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponPublishRepository;

import java.util.UUID;

public class FakeCouponPublishExistCheck implements CouponPublishExistCheck {

    private final CouponPublishRepository couponPublishRepository;

    public FakeCouponPublishExistCheck(CouponPublishRepository couponPublishRepository) {
        this.couponPublishRepository = couponPublishRepository;
    }

    @Override
    public boolean couponIssueCheck(Long memberId, UUID couponToken) {
        return couponPublishRepository.existsByMemberEntityIdAndCouponEntity_CouponToken(memberId, couponToken);
    }
}
