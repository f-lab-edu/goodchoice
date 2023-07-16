package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.infrastructure.FakeCouponCommand;
import com.flab.goodchoice.coupon.infrastructure.FakeCouponQuery;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponRepository;

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
