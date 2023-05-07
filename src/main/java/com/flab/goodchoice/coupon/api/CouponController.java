package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponService;
import com.flab.goodchoice.coupon.dto.CreateCouponRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CouponController {

    private final CouponService couponService;

    public CouponController(final CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping("/api/coupons")
    public void create(@RequestBody final CreateCouponRequest createCouponRequest) {
        couponService.create(createCouponRequest.couponName(), createCouponRequest.stock());
    }
}
