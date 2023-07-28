package com.flab.goodchoiceapi.coupon.infrastructure;

import com.flab.goodchoicecoupon.application.CouponIssueQuery;
import com.flab.goodchoicecoupon.domain.CouponIssue;
import com.flab.goodchoicecoupon.exception.CouponError;
import com.flab.goodchoicecoupon.exception.CouponException;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueRepository;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class FakeCouponIssueQuery implements CouponIssueQuery {

    private final CouponIssueRepository couponPublishRepository;

    public FakeCouponIssueQuery(CouponIssueRepository couponPublishRepository) {
        this.couponPublishRepository = couponPublishRepository;
    }

    @Override
    public List<CouponIssue> getCouponIssues(Long memberId) {
        return couponPublishRepository.findCouponHistoryFetchByMemberId(memberId).stream()
                .map(CouponIssueEntity::toCouponIssue)
                .collect(toList());
    }

    @Override
    public CouponIssue getCouponIssue(UUID couponPublishToken, Long memberId) {
        CouponIssueEntity couponPublishEntity = couponPublishRepository.findByCouponIssueTokenAndMemberId(couponPublishToken, memberId).orElseThrow(() -> new CouponException(CouponError.NOT_FOUND_COUPON));
        return couponPublishEntity.toCouponIssue();
    }
}
