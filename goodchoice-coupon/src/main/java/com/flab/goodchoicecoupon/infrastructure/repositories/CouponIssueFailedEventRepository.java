package com.flab.goodchoicecoupon.infrastructure.repositories;

import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueFailedEventEntity;

import java.util.List;
import java.util.Optional;

public interface CouponIssueFailedEventRepository {
    CouponIssueFailedEventEntity save(CouponIssueFailedEventEntity couponIssueFailedEventEntity);

    Optional<CouponIssueFailedEventEntity> findById(Long couponCouponIssueFailedEventId);

    List<CouponIssueFailedEventEntity> findAllByCouponToken(Long couponToken);
}
