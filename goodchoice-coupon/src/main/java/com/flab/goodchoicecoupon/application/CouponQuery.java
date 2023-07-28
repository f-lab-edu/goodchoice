package com.flab.goodchoicecoupon.application;

import com.flab.goodchoicecoupon.domain.Coupon;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface CouponQuery {
    List<Coupon> getCoupons();

    Coupon getCoupon(Long couponId);

    Coupon getCoupon(UUID couponToken);

    Coupon getCouponInfoLock(@Param("couponToken") UUID couponToken);
}
