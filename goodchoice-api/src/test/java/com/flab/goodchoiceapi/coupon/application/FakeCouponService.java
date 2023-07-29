package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.application.CouponQuery;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponCommand;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponQuery;
import com.flab.goodchoicecoupon.application.CouponCommand;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponRepository;

public class FakeCouponService {

    private final CouponRepository couponRepository;

    private CouponQuery couponQuery;
    private CouponCommand couponCommand;

    public FakeCouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    CouponService createCouponService() {
        couponQuery = new FakeCouponQuery(couponRepository);
        couponCommand = new FakeCouponCommand(couponRepository);

        return new CouponService(couponQuery, couponCommand);
    }
}
