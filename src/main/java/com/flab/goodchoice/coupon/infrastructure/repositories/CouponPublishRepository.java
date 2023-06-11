package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.infrastructure.entity.CouponPublishEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponPublishRepository {
    CouponPublishEntity save(CouponPublishEntity couponPublishHistory);

    int countByCouponEntityId(Long couponId);

    @Query("select cph from CouponPublishEntity cph join fetch cph.couponEntity")
    List<CouponPublishEntity> findCouponHistoryFetchByMemberId(Long memberId);

    Optional<CouponPublishEntity> findByCouponPublishTokenAndMemberEntityId(UUID couponPublishToken, Long memberId);

    boolean existsByMemberEntityIdAndCouponEntity_CouponToken(Long memberId, UUID couponToken);
}
