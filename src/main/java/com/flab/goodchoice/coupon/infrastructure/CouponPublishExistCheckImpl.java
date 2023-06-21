package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponPublishExistCheck;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponPublishRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CouponPublishExistCheckImpl implements CouponPublishExistCheck {

    private final CouponPublishRepository couponPublishRepository;

    public CouponPublishExistCheckImpl(CouponPublishRepository couponPublishRepository) {
        this.couponPublishRepository = couponPublishRepository;
    }

    @Override
    public boolean couponIssueCheck(Long memberId, UUID couponToken) {
        return couponPublishRepository.existsByMemberEntityIdAndCouponEntity_CouponToken(memberId, couponToken);
    }
}
