package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponUseService;
import com.flab.goodchoice.coupon.dto.*;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.UUID;

@RequestMapping("/api/coupons")
@RestController
public class CouponIssueController {

    private final CouponUseService couponUseService;

    public CouponIssueController(CouponUseService couponUseService) {
        this.couponUseService = couponUseService;
    }

    @PostMapping("/publish")
    public UUID createCouponPublish(@RequestBody CouponPublishRequest couponPublishRequest) {
        return couponUseService.createCouponPublish(couponPublishRequest.memberId(), couponPublishRequest.couponToken());
    }

    @PostMapping("/publish/redisson")
    public UUID createCouponPublishRedissonAop(@RequestBody CouponPublishRequest couponPublishRequest) {
        return couponUseService.createCouponPublishRedissonAop(couponPublishRequest.memberId(), couponPublishRequest.couponToken());
    }

    @GetMapping("/publish/{memberId}")
    public List<MemberSpecificCouponResponse> getMemberCoupon(@PathVariable Long memberId) {
        return couponUseService.getMemberCoupon(memberId);
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
