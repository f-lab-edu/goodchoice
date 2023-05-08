package com.flab.goodchoice.coupon.domain.repositories;

import com.flab.goodchoice.coupon.domain.Coupon;

import java.util.*;

public class InMemoryCouponRepository implements CouponRepository {
    private final Map<Long, Coupon> coupons = new HashMap<>();

    @Override
    public Coupon save(Coupon coupon) {
        coupons.put(coupons.size() + 1L, coupon);
        return coupon;
    }

    @Override
    public List<Coupon> findAll() {
        return new ArrayList<>(coupons.values());
    }

    @Override
    public Optional<Coupon> findById(Long couponId) {
        return Optional.ofNullable(coupons.get(couponId));
    }

    @Override
    public Optional<Coupon> findByCouponToken(UUID couponToken) {
        return coupons.values().stream()
                .filter(coupon -> coupon.getCouponToken().equals(couponToken))
                .findFirst();
    }
}
