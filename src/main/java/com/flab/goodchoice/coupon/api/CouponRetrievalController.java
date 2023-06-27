package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponRetrievalService;
import com.flab.goodchoice.coupon.dto.CouponRetrievalResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/coupons")
@RestController
public class CouponRetrievalController {

    private final CouponRetrievalService couponRetrievalService;

    public CouponRetrievalController(CouponRetrievalService couponRetrievalService) {
        this.couponRetrievalService = couponRetrievalService;
    }

    @GetMapping
    public List<CouponRetrievalResponse> getAllCoupons() {
        return couponRetrievalService.getAllCoupons();
    }

    @GetMapping("/{couponToken}")
    public CouponRetrievalResponse getCoupon(@PathVariable final UUID couponToken) {
        return couponRetrievalService.getCoupon(couponToken);
    }
}
