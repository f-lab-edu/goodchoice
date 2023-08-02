package com.flab.goodchoicecoupon.infrastructure.repositories;

import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueFailedEntity;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponIssueFailedRepository {
    CouponIssueFailedEntity save(CouponIssueFailedEntity couponIssueFailedEventEntity);

    Optional<CouponIssueFailedEntity> findById(Long couponCouponIssueFailedEventId);

    List<CouponIssueFailedEntity> findAllByCouponToken(UUID couponToken);
}
