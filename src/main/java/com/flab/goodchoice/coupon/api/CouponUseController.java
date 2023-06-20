package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponUseService;
import com.flab.goodchoice.coupon.dto.CouponUsedCancelInfoResponse;
import com.flab.goodchoice.coupon.dto.CouponUsedInfoResponse;
import com.flab.goodchoice.coupon.dto.CouponUsedRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RequestMapping("/api/coupons")
@RestController
public class CouponUseController {

    private final CouponUseService couponUseService;

    public CouponUseController(CouponUseService couponUseService) {
        this.couponUseService = couponUseService;
    }

    @PostMapping("/use")
    public CouponUsedInfoResponse useCoupon(@RequestBody @Valid CouponUsedRequest request) {
        return couponUseService.useCoupon(request.memberId(), request.couponPublishToken(), request.price());
    }

    @PostMapping("/cancel")
    public CouponUsedCancelInfoResponse usedCouponCancel(@RequestBody @Valid CouponUsedRequest request) {
        return couponUseService.usedCouponCancel(request.memberId(), request.couponPublishToken(), request.price());
    }
}
