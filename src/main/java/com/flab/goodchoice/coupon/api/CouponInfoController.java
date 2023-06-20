package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponInfoService;
import com.flab.goodchoice.coupon.dto.CouponInfoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/coupons")
@RestController
public class CouponInfoController {

    private final CouponInfoService couponInfoService;

    public CouponInfoController(CouponInfoService couponInfoService) {
        this.couponInfoService = couponInfoService;
    }

    @GetMapping
    public List<CouponInfoResponse> getAllCoupons() {
        return couponInfoService.getAllCoupons();
    }

    @GetMapping("/{couponToken}")
    public CouponInfoResponse getCoupon(@PathVariable final UUID couponToken) {
        return couponInfoService.getCoupon(couponToken);
    }
}
