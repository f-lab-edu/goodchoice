package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponQuery;
import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponRepository;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

public class FakeCouponQuery implements CouponQuery {

    private final CouponRepository couponRepository;

    public FakeCouponQuery(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public List<Coupon> getCoupons() {
        return couponRepository.findAll().stream()
                .map(CouponEntity::toCoupon)
                .collect(toList());
    }

    @Override
    public Coupon getCouponInfo(Long couponId) {
        CouponEntity couponEntity = couponRepository.findById(couponId).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        return couponEntity.toCoupon();
    }

    @Override
    public Coupon getCouponInfo(UUID couponToken) {
        CouponEntity couponEntity = couponRepository.findByCouponToken(couponToken).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        return couponEntity.toCoupon();
    }

    @Override
    public Coupon getCouponInfoLock(UUID couponToken) {
        CouponEntity couponEntity = couponRepository.findLockByCouponToken(couponToken).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        return couponEntity.toCoupon();
    }
}
