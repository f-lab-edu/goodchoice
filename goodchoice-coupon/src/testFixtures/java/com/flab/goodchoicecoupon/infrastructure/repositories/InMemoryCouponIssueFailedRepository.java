package com.flab.goodchoicecoupon.infrastructure.repositories;

import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueFailedEntity;

import java.util.*;

public class InMemoryCouponIssueFailedRepository implements CouponIssueFailedRepository {
    private final Map<Long, CouponIssueFailedEntity> couponIssueFailedEventEntitys = new HashMap<>();

    @Override
    public CouponIssueFailedEntity save(CouponIssueFailedEntity couponIssueFailedEventEntity) {
        if (couponIssueFailedEventEntitys.containsValue(couponIssueFailedEventEntity)) {
            for (Long key : couponIssueFailedEventEntitys.keySet()) {
                if (couponIssueFailedEventEntitys.get(key).equals(couponIssueFailedEventEntity)) {
                    couponIssueFailedEventEntitys.put(key, couponIssueFailedEventEntity);
                    return couponIssueFailedEventEntity;
                }
            }
        }

        couponIssueFailedEventEntitys.put(couponIssueFailedEventEntitys.size() + 1L, couponIssueFailedEventEntity);
        return couponIssueFailedEventEntity;
    }

    @Override
    public Optional<CouponIssueFailedEntity> findById(Long couponCouponIssueFailedEventId) {
        return Optional.ofNullable(couponIssueFailedEventEntitys.get(couponCouponIssueFailedEventId));
    }

    @Override
    public List<CouponIssueFailedEntity> findAllByCouponToken(UUID couponToken) {
        return couponIssueFailedEventEntitys.values().stream()
                .filter(entity -> entity.getCouponToken().equals(couponToken))
                .toList();
    }
}
