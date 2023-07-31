package com.flab.goodchoicecoupon.infrastructure.repositories;

import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueFailedEventEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryCouponIssueFailedEventRepository implements CouponIssueFailedEventRepository {
    private final Map<Long, CouponIssueFailedEventEntity> couponIssueFailedEventEntitys = new HashMap<>();

    @Override
    public CouponIssueFailedEventEntity save(CouponIssueFailedEventEntity couponIssueFailedEventEntity) {
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
    public Optional<CouponIssueFailedEventEntity> findById(Long couponCouponIssueFailedEventId) {
        return Optional.ofNullable(couponIssueFailedEventEntitys.get(couponCouponIssueFailedEventId));
    }

    @Override
    public List<CouponIssueFailedEventEntity> findAllByCouponToken(Long couponToken) {
        return couponIssueFailedEventEntitys.values().stream()
                .filter(entity -> entity.getCouponToken().equals(couponToken))
                .toList();
    }
}
