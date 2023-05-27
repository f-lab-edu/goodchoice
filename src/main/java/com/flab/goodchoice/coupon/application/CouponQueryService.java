package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.dto.CouponInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class CouponQueryService {

    private final CouponQuery couponQuery;

    public CouponQueryService(CouponQuery couponQuery) {
        this.couponQuery = couponQuery;
    }

    public List<CouponInfoResponse> getAllCoupons() {
        return couponQuery.findAll().stream()
                .map(coupon -> new CouponInfoResponse(coupon.getCouponToken(), coupon.getCouponName(), coupon.getStock(), coupon.getState()))
                .toList();
    }

    public CouponInfoResponse getCoupon(final UUID couponToken) {
        Coupon coupon = couponQuery.findByCouponToken(couponToken);
        return new CouponInfoResponse(coupon.getCouponToken(), coupon.getCouponName(), coupon.getStock(), coupon.getState());
    }
}
