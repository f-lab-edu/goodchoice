package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.infrastructure.entity.CouponUseHistoryEntity;

import java.util.Optional;

public interface CouponUseHistoryRepository {
    CouponUseHistoryEntity save(CouponUseHistoryEntity couponUseHistoryEntity);

    Optional<CouponUseHistoryEntity> findByMemberIdAndCouponEntityId(Long memberId, Long couponEntityId);
}
