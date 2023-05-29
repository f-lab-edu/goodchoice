package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponCommandService;
import com.flab.goodchoice.coupon.application.CouponQueryService;
import com.flab.goodchoice.coupon.dto.CouponInfoResponse;
import com.flab.goodchoice.coupon.dto.CreateCouponRequest;
import com.flab.goodchoice.coupon.dto.ModifyCouponRequest;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/coupons")
@RestController
public class CouponController {

    private final CouponQueryService couponQueryService;
    private final CouponCommandService couponCommandService;

    public CouponController(CouponQueryService couponQueryService, CouponCommandService couponCommandService) {
        this.couponQueryService = couponQueryService;
        this.couponCommandService = couponCommandService;
    }

    @PostMapping
    public UUID createCoupon(final @RequestBody @Valid CreateCouponRequest createCouponRequest) {
        return couponCommandService.createCoupon(createCouponRequest);
    }

    @GetMapping
    public List<CouponInfoResponse> getAllCoupons() {
        return couponQueryService.getAllCoupons();
    }

    @GetMapping("/{couponToken}")
    public CouponInfoResponse getCoupon(@PathVariable final UUID couponToken) {
        return couponQueryService.getCoupon(couponToken);
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
