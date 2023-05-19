package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponUseService;
import com.flab.goodchoice.coupon.dto.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequestMapping()
@RestController
public class CouponIssueController {

    private final CouponUseService couponUseService;

    public CouponIssueController(CouponUseService couponUseService) {
        this.couponUseService = couponUseService;
    }

    @PostMapping("/api/coupons/publish")
    public UUID couponPublish(@RequestBody CouponPublishRequest couponPublishRequest) {
        return couponUseService.couponPublish(couponPublishRequest.memberId(), couponPublishRequest.couponToken());
    }

    @GetMapping("/api/coupons/publish/{memberId}")
    public List<MemberSpecificCouponResponse> getMemberCoupon(@PathVariable Long memberId) {
        return couponUseService.getMemberCoupon(memberId);
    }

    @PostMapping("/api/coupons/used")
    public CouponUsedInfoResponse couponUsed(@RequestBody CouponUsedRequest request) {
        return couponUseService.couponUsed(request.memberId(), request.couponToken(), request.price());
    }

    @PostMapping("/cancel")
    public CouponUsedCancelInfoResponse couponUsedCancel(@RequestBody CouponUsedRequest couponUsedRequest) {
        return couponUseService.couponUsedCancel(couponUsedRequest.couponToken(), couponUsedRequest.price());
    }
}
