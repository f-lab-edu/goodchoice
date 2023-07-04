package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponIssueChecker;
import com.flab.goodchoice.coupon.exception.CouponError;
import com.flab.goodchoice.coupon.exception.CouponException;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponIssueRepository;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CouponIssueExistCheckImpl implements CouponIssueChecker {

    private final CouponIssueRepository couponIssueRepository;

    public CouponIssueExistCheckImpl(CouponIssueRepository couponIssueRepository) {
        this.couponIssueRepository = couponIssueRepository;
    }

    @Override
    public void duplicateCouponIssueCheck(Long memberId, UUID couponToken) {
        boolean existsCoupon = couponIssueRepository.existsByMemberEntityIdAndCouponEntity_CouponToken(memberId, couponToken);
        if (existsCoupon) {
            throw new CouponException(CouponError.NOT_DUPLICATION_COUPON);
        }
    }
}
