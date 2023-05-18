package com.flab.goodchoice.coupon.domain.repositories;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponPublishHistory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryCouponHistoryPublishRepository implements CouponHistoryPublishRepository {
    private final Map<Long, CouponPublishHistory> couponPublishHistorys = new HashMap<>();

    @Override
    public CouponPublishHistory save(CouponPublishHistory couponPublishHistory) {
        couponPublishHistorys.put(couponPublishHistorys.size() + 1L, couponPublishHistory);
        return couponPublishHistory;
    }

    @Override
    public int countByCoupon(Coupon coupon) {
        return couponPublishHistorys.size();
    }

    @Override
    public List<CouponPublishHistory> findCouponHistoryFetchByMemberId(Long memberId) {
        return couponPublishHistorys.values().stream()
                .filter(couponPublishHistory -> couponPublishHistory.getMemberId().equals(memberId))
                .toList();
    }
}
