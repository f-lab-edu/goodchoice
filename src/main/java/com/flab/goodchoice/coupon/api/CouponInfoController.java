package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponQueryService;
import com.flab.goodchoice.coupon.dto.CouponInfoResponse;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/coupons")
@RestController
public class CouponInfoController {

    private final CouponQueryService couponQueryService;

    public CouponInfoController(CouponQueryService couponQueryService) {
        this.couponQueryService = couponQueryService;
    }

    @GetMapping
    public List<CouponInfoResponse> getAllCoupons() {
        return couponQueryService.getAllCoupons();
    }

    @GetMapping("/{couponToken}")
    public CouponInfoResponse getCoupon(@PathVariable final UUID couponToken) {
        return couponQueryService.getCoupon(couponToken);
    }
}
