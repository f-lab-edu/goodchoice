package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponIssueExistCheck;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponIssueRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CouponIssueExistCheckImpl implements CouponIssueExistCheck {

    private final CouponIssueRepository couponIssueRepository;

    public CouponIssueExistCheckImpl(CouponIssueRepository couponIssueRepository) {
        this.couponIssueRepository = couponIssueRepository;
    }

    @Override
    public boolean duplicateCouponIssue(Long memberId, UUID couponToken) {
        return couponIssueRepository.existsByMemberEntityIdAndCouponEntity_CouponToken(memberId, couponToken);
    }
}
