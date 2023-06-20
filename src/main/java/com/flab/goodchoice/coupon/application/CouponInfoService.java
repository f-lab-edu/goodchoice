package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.dto.CouponInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class CouponInfoService {

    private final CouponQuery couponQuery;

    public CouponInfoService(CouponQuery couponQuery) {
        this.couponQuery = couponQuery;
    }

    public List<CouponInfoResponse> getAllCoupons() {
        return couponQuery.findAll().stream()
                .map(CouponInfoResponse::of)
                .toList();
    }

    public CouponInfoResponse getCoupon(final UUID couponToken) {
        Coupon coupon = couponQuery.findByCouponToken(couponToken);
        return CouponInfoResponse.of(coupon);
    }
}
