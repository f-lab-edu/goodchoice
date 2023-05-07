package com.flab.goodchoice.coupon.domain.repositories;

import com.flab.goodchoice.coupon.domain.Coupon;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryCouponRepository implements CouponRepository {
    private final Map<Long, Coupon> coupons = new HashMap<>();

    @Override
    public Coupon save(Coupon coupon) {
        coupons.put(coupons.size() + 1L, coupon);
        return coupon;
    }

    @Override
    public Optional<Coupon> findById(Long couponId) {
        return Optional.ofNullable(coupons.get(couponId));
    }
}
