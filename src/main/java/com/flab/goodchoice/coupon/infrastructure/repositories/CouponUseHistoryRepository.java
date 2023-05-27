package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.infrastructure.entity.MemberEntity;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponUseHistoryEntity;

import java.util.Optional;

public interface CouponUseHistoryRepository {
    CouponUseHistoryEntity save(CouponUseHistoryEntity couponUseHistoryEntity);

    Optional<CouponUseHistoryEntity> findByMemberAndCouponEntity(MemberEntity member, CouponEntity couponEntity);
}
