package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponIssueExistCheck;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponIssueRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CouponIssueExistCheckImpl implements CouponIssueExistCheck {

    private final CouponIssueRepository couponPublishRepository;

    public CouponIssueExistCheckImpl(CouponIssueRepository couponPublishRepository) {
        this.couponPublishRepository = couponPublishRepository;
    }

    @Override
    public boolean couponIssueCheck(Long memberId, UUID couponToken) {
        return couponPublishRepository.existsByMemberEntityIdAndCouponEntity_CouponToken(memberId, couponToken);
    }
}
