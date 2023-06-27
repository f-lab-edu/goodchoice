package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponIssueQuery;
import com.flab.goodchoice.coupon.domain.CouponIssue;
import com.flab.goodchoice.coupon.exception.CouponError;
import com.flab.goodchoice.coupon.exception.CouponException;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponIssueEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponIssueRepository;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class FakeCouponIssueQuery implements CouponIssueQuery {

    private final CouponIssueRepository couponPublishRepository;

    public FakeCouponIssueQuery(CouponIssueRepository couponPublishRepository) {
        this.couponPublishRepository = couponPublishRepository;
    }

    @Override
    public List<CouponIssue> getCouponIssue(Long memberId) {
        return couponPublishRepository.findCouponHistoryFetchByMemberId(memberId).stream()
                .map(CouponIssueEntity::toCouponIssue)
                .collect(toList());
    }

    @Override
    public CouponIssue getCouponIssue(UUID couponPublishToken, Long memberId) {
        CouponIssueEntity couponPublishEntity = couponPublishRepository.findByCouponIssueTokenAndMemberEntityId(couponPublishToken, memberId).orElseThrow(() -> new CouponException(CouponError.NOT_FOUND_COUPON));
        return couponPublishEntity.toCouponIssue();
    }
}
