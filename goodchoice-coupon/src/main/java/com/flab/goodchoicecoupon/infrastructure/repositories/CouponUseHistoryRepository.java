package com.flab.goodchoicecoupon.infrastructure.repositories;

import com.flab.goodchoicecoupon.infrastructure.entity.CouponUseHistoryEntity;

import java.util.Optional;

public interface CouponUseHistoryRepository {
    CouponUseHistoryEntity save(CouponUseHistoryEntity couponUseHistoryEntity);

    Optional<CouponUseHistoryEntity> findByMemberIdAndCouponEntityId(Long memberId, Long couponEntityId);
}
