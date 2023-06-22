package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.infrastructure.entity.CouponEntity;

import java.util.*;

public class InMemoryCouponRepository implements CouponRepository {
    private final Map<Long, CouponEntity> coupons = new HashMap<>();

    @Override
    public CouponEntity save(CouponEntity coupon) {
        if (coupons.containsValue(coupon)) {
            for (Long key : coupons.keySet()) {
                if (coupons.get(key).equals(coupon)) {
                    coupons.put(key, coupon);
                    return coupon;
                }
            }
        }

        coupons.put(coupons.size() + 1L, coupon);
        return coupon;
    }

    @Override
    public List<CouponEntity> findAll() {
        return new ArrayList<>(coupons.values());
    }

    @Override
    public Optional<CouponEntity> findById(Long couponId) {
        return Optional.ofNullable(coupons.get(couponId));
    }

    @Override
    public Optional<CouponEntity> findByCouponToken(UUID couponToken) {
        return coupons.values().stream()
                .filter(coupon -> coupon.getCouponToken().equals(couponToken))
                .findFirst();
    }

    @Override
    public Optional<CouponEntity> findLockByCouponToken(UUID couponToken) {
        return coupons.values().stream()
                .filter(coupon -> coupon.getCouponToken().equals(couponToken))
                .findFirst();
    }
}
