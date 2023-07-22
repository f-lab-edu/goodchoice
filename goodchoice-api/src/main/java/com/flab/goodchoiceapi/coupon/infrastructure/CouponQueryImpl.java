package com.flab.goodchoiceapi.coupon.infrastructure;

import com.flab.goodchoiceapi.coupon.application.CouponQuery;
import com.flab.goodchoicecoupon.domain.Coupon;
import com.flab.goodchoicecoupon.exception.CouponError;
import com.flab.goodchoicecoupon.exception.CouponException;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Transactional(readOnly = true)
@Component
public class CouponQueryImpl implements CouponQuery {

    private final CouponRepository couponRepository;

    public CouponQueryImpl(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public List<Coupon> getCoupons() {
        return couponRepository.findAll().stream()
                .map(CouponEntity::toCoupon)
                .collect(toList());
    }

    @Override
    public Coupon getCoupon(Long couponId) {
        CouponEntity couponEntity = couponRepository.findById(couponId).orElseThrow(() -> new CouponException(CouponError.NOT_FOUND_COUPON));
        return couponEntity.toCoupon();
    }

    @Override
    public Coupon getCoupon(UUID couponToken) {
        CouponEntity couponEntity = couponRepository.findByCouponToken(couponToken).orElseThrow(() -> new CouponException(CouponError.NOT_FOUND_COUPON));
        return couponEntity.toCoupon();
    }

    @Override
    public Coupon getCouponInfoLock(UUID couponToken) {
        CouponEntity couponEntity = couponRepository.findLockByCouponToken(couponToken).orElseThrow(() -> new CouponException(CouponError.NOT_FOUND_COUPON));
        return couponEntity.toCoupon();
    }
}
