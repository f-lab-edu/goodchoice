package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.dto.CouponRetrievalResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class CouponRetrievalService {

    private final CouponQuery couponQuery;

    public CouponRetrievalService(CouponQuery couponQuery) {
        this.couponQuery = couponQuery;
    }

    public List<CouponRetrievalResponse> getAllCoupons() {
        return couponQuery.getCoupons().stream()
                .map(CouponRetrievalResponse::of)
                .toList();
    }

    public CouponRetrievalResponse getCoupon(final UUID couponToken) {
        Coupon coupon = couponQuery.getCouponInfo(couponToken);
        return CouponRetrievalResponse.of(coupon);
    }
}
