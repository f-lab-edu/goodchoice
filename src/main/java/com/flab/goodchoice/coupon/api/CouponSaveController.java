package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponCommandService;
import com.flab.goodchoice.coupon.dto.CreateCouponRequest;
import com.flab.goodchoice.coupon.dto.ModifyCouponRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("/api/coupons")
@RestController
public class CouponSaveController {

    private final CouponCommandService couponCommandService;

    public CouponSaveController(CouponCommandService couponCommandService) {
        this.couponCommandService = couponCommandService;
    }

    @PostMapping
    public UUID createCoupon(final @RequestBody @Valid CreateCouponRequest createCouponRequest) {
        return couponCommandService.createCoupon(createCouponRequest);
    }

    @PutMapping("/{couponToken}")
    public UUID modifyCoupon(@PathVariable final UUID couponToken, final @RequestBody @Valid ModifyCouponRequest modifyCouponRequest) {
        return couponCommandService.modifyCoupon(couponToken, modifyCouponRequest.couponName(), modifyCouponRequest.stock());
    }

    @DeleteMapping("/{couponToken}")
    public UUID removeCoupon(@PathVariable final UUID couponToken) {
        return couponCommandService.removeCoupon(couponToken);
    }
}
