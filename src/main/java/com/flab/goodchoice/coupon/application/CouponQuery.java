package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CouponQuery {
    List<Coupon> findAll();

    Coupon findById(Long couponId);

    Coupon findByCouponToken(UUID couponToken);

    Coupon findByCouponTokenLock(@Param("couponToken") UUID couponToken);
}
