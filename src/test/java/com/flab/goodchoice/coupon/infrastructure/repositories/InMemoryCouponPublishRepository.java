package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.infrastructure.entity.CouponPublishEntity;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryCouponPublishRepository implements CouponPublishRepository {
    private final Map<Long, CouponPublishEntity> couponPublishs = new HashMap<>();

    @Override
    public CouponPublishEntity save(CouponPublishEntity couponPublishHistory) {
        couponPublishs.put(couponPublishs.size() + 1L, couponPublishHistory);
        return couponPublishHistory;
    }

    @Override
    public int countByCouponEntityId(Long couponId) {
        return couponPublishs.size();
    }

    @Override
    public List<CouponPublishEntity> findCouponHistoryFetchByMemberId(Long memberId) {
        return couponPublishs.values().stream()
                .filter(couponPublishHistory -> couponPublishHistory.getMemberId().equals(memberId))
                .toList();
    }

    @Override
    public Optional<CouponPublishEntity> findByCouponEntityIdAndMemberId(Long couponId, Long memberId) {
        return couponPublishs.values().stream()
                .filter(couponPublish -> couponPublish.getCouponEntity().getId().equals(couponId))
                .filter(couponPublish -> couponPublish.getMemberId().equals(memberId))
                .findFirst();
    }
}
