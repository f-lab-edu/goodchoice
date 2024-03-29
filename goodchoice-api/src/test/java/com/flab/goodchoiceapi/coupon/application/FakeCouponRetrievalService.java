package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.infrastructure.FakeCouponQuery;
import com.flab.goodchoicecoupon.application.CouponQuery;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponRepository;

public class FakeCouponRetrievalService {

    private final CouponRepository couponRepository;
    private CouponQuery couponQuery;

    public FakeCouponRetrievalService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    CouponRetrievalService createCouponRetrievalService() {
        couponQuery = new FakeCouponQuery(couponRepository);

        return new CouponRetrievalService(couponQuery);
    }
}
