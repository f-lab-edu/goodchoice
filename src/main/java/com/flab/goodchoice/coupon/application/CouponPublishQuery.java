package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.CouponPublish;

import java.util.List;

public interface CouponPublishQuery {
    int countByCouponEntityId(Long couponId);

    List<CouponPublish> findCouponHistoryFetchByMemberId(Long memberId);

    CouponPublish findByCouponEntityIdAndMemberEntityId(Long couponId, Long memberId);
}
