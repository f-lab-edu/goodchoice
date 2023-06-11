package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.infrastructure.entity.CouponPublishEntity;

import java.util.*;

public class InMemoryCouponPublishRepository implements CouponPublishRepository {
    private final Map<Long, CouponPublishEntity> couponPublishs = new HashMap<>();

    @Override
    public CouponPublishEntity save(CouponPublishEntity couponPublishHistory) {
        if (couponPublishs.containsValue(couponPublishHistory)) {
            for (Long key : couponPublishs.keySet()) {
                if (couponPublishs.get(key).equals(couponPublishHistory)) {
                    couponPublishs.put(key, couponPublishHistory);
                    return couponPublishHistory;
                }
            }
        }

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
                .filter(couponPublishHistory -> couponPublishHistory.getMemberEntity().getId().equals(memberId))
                .toList();
    }

    @Override
    public Optional<CouponPublishEntity> findByCouponPublishTokenAndMemberEntityId(UUID couponPublishToken, Long memberId) {
        return couponPublishs.values().stream()
                .filter(couponPublish -> couponPublish.getCouponPublishToken().equals(couponPublishToken))
                .filter(couponPublish -> couponPublish.getMemberEntity().getId().equals(memberId))
                .findFirst();
    }

    @Override
    public boolean existsByMemberEntityIdAndCouponEntity_CouponToken(Long memberId, UUID couponToken) {
        return couponPublishs.values().stream()
                .anyMatch(entity -> Objects.equals(entity.getMemberEntity().getId(), memberId) && Objects.equals(entity.getCouponEntity().getCouponToken(), couponToken));
    }
}
