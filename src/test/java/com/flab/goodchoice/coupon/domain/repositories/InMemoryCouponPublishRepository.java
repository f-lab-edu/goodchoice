package com.flab.goodchoice.coupon.domain.repositories;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponPublish;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class InMemoryCouponPublishRepository implements CouponPublishRepository {
    private final Map<Long, CouponPublish> couponPublishs = new HashMap<>();

    @Override
    public CouponPublish save(CouponPublish couponPublishHistory) {
        couponPublishs.put(couponPublishs.size() + 1L, couponPublishHistory);
        return couponPublishHistory;
    }

    @Override
    public int countByCoupon(Coupon coupon) {
        return couponPublishs.size();
    }

    @Override
    public List<CouponPublish> findCouponHistoryFetchByMemberId(Long memberId) {
        return couponPublishs.values().stream()
                .filter(couponPublishHistory -> couponPublishHistory.getMemberId().equals(memberId))
                .toList();
    }

    @Override
    public Optional<CouponPublish> findByCouponAndMemberId(Coupon coupon, Long memberId) {
        return couponPublishs.values().stream()
                .filter(couponPublish -> couponPublish.getCoupon().getId().equals(coupon.getId()))
                .filter(couponPublish -> couponPublish.getMemberId().equals(memberId))
                .findFirst();
    }
}
