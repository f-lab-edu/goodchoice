package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponSaveService;
import com.flab.goodchoice.coupon.dto.CreateCouponRequest;
import com.flab.goodchoice.coupon.dto.ModifyCouponRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/api/coupons")
@RestController
public class CouponController {

    private final CouponSaveService couponSaveService;

    public CouponController(CouponSaveService couponSaveService) {
        this.couponSaveService = couponSaveService;
    }

    @PostMapping
    public UUID createCoupon(final @RequestBody @Valid CreateCouponRequest createCouponRequest) {
        return couponSaveService.createCoupon(createCouponRequest);
    }

    @PutMapping("/{couponToken}")
    public UUID modifyCoupon(@PathVariable final UUID couponToken, final @RequestBody @Valid ModifyCouponRequest modifyCouponRequest) {
        return couponSaveService.modifyCoupon(couponToken, modifyCouponRequest.couponName(), modifyCouponRequest.stock());
    }

    @DeleteMapping("/{couponToken}")
    public UUID removeCoupon(@PathVariable final UUID couponToken) {
        return couponSaveService.removeCoupon(couponToken);
    }
}
