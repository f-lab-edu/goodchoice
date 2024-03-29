package com.flab.goodchoicecoupon.infrastructure.repositories;

import com.flab.goodchoicecoupon.infrastructure.entity.CouponUseHistoryEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCouponUseHistoryEntityRepository implements CouponUseHistoryRepository {
    private final Map<Long, CouponUseHistoryEntity> couponUseHistoryEntitys = new HashMap<>();

    @Override
    public CouponUseHistoryEntity save(CouponUseHistoryEntity couponUseHistoryEntity) {
        couponUseHistoryEntitys.put(couponUseHistoryEntitys.size() + 1L, couponUseHistoryEntity);
        return couponUseHistoryEntity;
    }

    @Override
    public Optional<CouponUseHistoryEntity> findByMemberIdAndCouponEntityId(Long memberId, Long couponEntityId) {
        return couponUseHistoryEntitys.values().stream()
                .filter(couponUseHistoryEntity -> couponUseHistoryEntity.getMemberId().equals(memberId))
                .filter(couponUseHistoryEntity -> couponUseHistoryEntity.getCouponEntity().getId().equals(couponEntityId))
                .findFirst();
    }
}
