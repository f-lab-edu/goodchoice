package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.CouponPublish;

import java.util.List;
import java.util.UUID;

public interface CouponPublishQuery {
    List<CouponPublish> findCouponHistoryFetchByMemberId(Long memberId);

    CouponPublish findByCouponPublishTokenAndMemberEntityId(UUID couponPublishToken, Long memberId);

    boolean existsByMemberEntityIdAndCouponPublishToken(Long memberId, UUID couponToken);
}
