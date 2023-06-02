package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponQuery;
import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
public class CouponQueryImpl implements CouponQuery {

    private final CouponRepository couponRepository;

    public CouponQueryImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public List<Coupon> findAll() {
        return couponRepository.findAll().stream()
                .map(CouponEntity::toCoupon)
                .collect(toList());
    }

    @Override
    public Coupon findById(Long couponId) {
        CouponEntity couponEntity = couponRepository.findById(couponId).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        return couponEntity.toCoupon();
    }

    @Override
    public Coupon findByCouponToken(UUID couponToken) {
        CouponEntity couponEntity = couponRepository.findByCouponToken(couponToken).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        return couponEntity.toCoupon();
    }

    @Override
    public Coupon findByCouponTokenLock(UUID couponToken) {
        CouponEntity couponEntity = couponRepository.findLockByCouponToken(couponToken).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        return couponEntity.toCoupon();
    }
}
