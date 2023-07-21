package com.flab.goodchoicecoupon.infrastructure.repositories;

import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueEntity;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponIssueRepository {
    CouponIssueEntity save(CouponIssueEntity couponIssueEntity);

    int countByCouponEntityId(Long couponId);

    @Query("select cih from CouponIssueEntity cih join fetch cih.couponEntity")
    List<CouponIssueEntity> findCouponHistoryFetchByMemberId(Long memberId);

    Optional<CouponIssueEntity> findByCouponIssueTokenAndMemberEntityId(UUID couponIssueToken, Long memberId);

    boolean existsByMemberEntityIdAndCouponEntity_CouponToken(Long memberId, UUID couponToken);
}
