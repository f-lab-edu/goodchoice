package com.flab.goodchoice.coupon.domain.repositories;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponPublish;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCouponPublishRepository implements CouponPublishRepository {
    private final Map<Long, CouponPublish> couponPublishHistorys = new HashMap<>();

    @Override
    public CouponPublish save(CouponPublish couponPublishHistory) {
        couponPublishHistorys.put(couponPublishHistorys.size() + 1L, couponPublishHistory);
        return couponPublishHistory;
    }

    @Override
    public int countByCoupon(Coupon coupon) {
        return couponPublishHistorys.size();
    }

    @Override
    public List<CouponPublish> findCouponHistoryFetchByMemberId(Long memberId) {
        return couponPublishHistorys.values().stream()
                .filter(couponPublishHistory -> couponPublishHistory.getMemberId().equals(memberId))
                .toList();
    }
}
