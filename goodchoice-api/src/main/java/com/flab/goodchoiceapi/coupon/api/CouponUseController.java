package com.flab.goodchoiceapi.coupon.api;

import com.flab.goodchoiceapi.coupon.application.CouponUseService;
import com.flab.goodchoiceapi.coupon.dto.CouponUsedCancelInfoResponse;
import com.flab.goodchoiceapi.coupon.dto.CouponUsedInfoResponse;
import com.flab.goodchoiceapi.coupon.dto.CouponUseRequest;
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
    public CouponUsedInfoResponse useCoupon(@RequestBody @Valid CouponUseRequest request) {
        return couponUseService.useCoupon(request.memberId(), request.couponPublishToken(), request.price());
    }

    @PostMapping("/cancel")
    public CouponUsedCancelInfoResponse usedCouponCancel(@RequestBody @Valid CouponUseRequest request) {
        return couponUseService.usedCouponCancel(request.memberId(), request.couponPublishToken(), request.price());
    }
}
