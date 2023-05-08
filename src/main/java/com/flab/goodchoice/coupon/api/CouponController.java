package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponService;
import com.flab.goodchoice.coupon.dto.CouponInfoResponse;
import com.flab.goodchoice.coupon.dto.CreateCouponRequest;
import com.flab.goodchoice.coupon.dto.ModifyCouponRequest;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping("/api/coupons")
@RestController
public class CouponController {

    private final CouponService couponService;

    public CouponController(final CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public void create(@RequestBody final CreateCouponRequest createCouponRequest) {
        couponService.create(createCouponRequest.couponName(), createCouponRequest.stock());
    }

    @GetMapping
    public List<CouponInfoResponse> getAllCoupons() {
        return couponService.getAllCoupons();
    }

    @GetMapping("/{couponToken}")
    public CouponInfoResponse getCouponDetail(@PathVariable final UUID couponToken) {
        return couponService.getCouponDetail(couponToken);
    }

    @PutMapping("/{couponToken}")
    public void modifyCoupon(@PathVariable final UUID couponToken, @RequestBody final ModifyCouponRequest modifyCouponRequest) {
        couponService.modifyCoupon(couponToken, modifyCouponRequest.couponName(), modifyCouponRequest.stock());
    }
}
