package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponIssuanceService;
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
    private final CouponIssuanceService couponIssuanceService;

    public CouponIssueController(CouponUseService couponUseService, CouponIssuanceService couponIssuanceService) {
        this.couponUseService = couponUseService;
        this.couponIssuanceService = couponIssuanceService;
    }

    @PostMapping("/publish")
    public UUID createCouponPublish(@RequestBody CouponPublishRequest couponPublishRequest) {
        return couponIssuanceService.couponIssuance(couponPublishRequest.memberId(), couponPublishRequest.couponToken());
    }

    @PostMapping("/publish/redisson")
    public UUID createCouponPublishRedissonAop(@RequestBody CouponPublishRequest couponPublishRequest) {
        return couponIssuanceService.couponIssuanceRedissonAop(couponPublishRequest.memberId(), couponPublishRequest.couponToken());
    }

    @GetMapping("/publish/{memberId}")
    public List<MemberSpecificCouponResponse> getMemberCoupon(@PathVariable Long memberId) {
        return couponIssuanceService.getMemberCoupon(memberId);
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
