package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponPublishService;
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
    private final CouponPublishService couponPublishService;

    public CouponIssueController(CouponUseService couponUseService, CouponPublishService couponPublishService) {
        this.couponUseService = couponUseService;
        this.couponPublishService = couponPublishService;
    }

    @PostMapping("/publish")
    public UUID createCouponPublish(@RequestBody CouponPublishRequest couponPublishRequest) {
        return couponPublishService.createCouponPublish(couponPublishRequest.memberId(), couponPublishRequest.couponToken());
    }

    @PostMapping("/publish/redisson")
    public UUID createCouponPublishRedissonAop(@RequestBody CouponPublishRequest couponPublishRequest) {
        return couponPublishService.createCouponPublishRedissonAop(couponPublishRequest.memberId(), couponPublishRequest.couponToken());
    }

    @GetMapping("/publish/{memberId}")
    public List<MemberSpecificCouponResponse> getMemberCoupon(@PathVariable Long memberId) {
        return couponPublishService.getMemberCoupon(memberId);
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
