package com.flab.goodchoiceapi.coupon.infrastructure.repositories;

import com.flab.goodchoiceapi.coupon.infrastructure.entity.CouponIssueEntity;

import java.util.*;

public class InMemoryCouponIssueRepository implements CouponIssueRepository {
    private final Map<Long, CouponIssueEntity> couponPublishs = new HashMap<>();

    @Override
    public CouponIssueEntity save(CouponIssueEntity couponPublishHistory) {
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
    public List<CouponIssueEntity> findCouponHistoryFetchByMemberId(Long memberId) {
        return couponPublishs.values().stream()
                .filter(couponPublishHistory -> couponPublishHistory.getMemberEntity().getId().equals(memberId))
                .toList();
    }

    @Override
    public Optional<CouponIssueEntity> findByCouponIssueTokenAndMemberEntityId(UUID couponPublishToken, Long memberId) {
        return couponPublishs.values().stream()
                .filter(couponPublish -> couponPublish.getCouponIssueToken().equals(couponPublishToken))
                .filter(couponPublish -> couponPublish.getMemberEntity().getId().equals(memberId))
                .findFirst();
    }

    @Override
    public boolean existsByMemberEntityIdAndCouponEntity_CouponToken(Long memberId, UUID couponToken) {
        return couponPublishs.values().stream()
                .anyMatch(entity -> Objects.equals(entity.getMemberEntity().getId(), memberId) && Objects.equals(entity.getCouponEntity().getCouponToken(), couponToken));
    }
}
