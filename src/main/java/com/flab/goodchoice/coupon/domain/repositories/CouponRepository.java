package com.flab.goodchoice.coupon.domain.repositories;

import com.flab.goodchoice.coupon.domain.Coupon;

import java.util.List;
import java.util.Optional;

public interface CouponRepository {
    Coupon save(Coupon coupon);

    List<Coupon> findAll();

    Optional<Coupon> findById(Long couponId);
}
