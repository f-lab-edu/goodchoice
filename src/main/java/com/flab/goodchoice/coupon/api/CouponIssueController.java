package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponIssueService;
import com.flab.goodchoice.coupon.dto.CouponPublishRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("/api/coupons")
@RestController
public class CouponIssueController {

    private final CouponIssueService couponIssueService;

    public CouponIssueController(CouponIssueService couponIssueService) {
        this.couponIssueService = couponIssueService;
    }

    @PostMapping("/publish")
    public UUID createCouponPublish(@RequestBody CouponPublishRequest couponPublishRequest) {
        return couponIssueService.couponIssuance(couponPublishRequest.memberId(), couponPublishRequest.couponToken());
    }

    @PostMapping("/publish/redisson")
    public UUID createCouponPublishRedissonAop(@RequestBody CouponPublishRequest couponPublishRequest) {
        return couponIssueService.couponIssuanceRedissonAop(couponPublishRequest.memberId(), couponPublishRequest.couponToken());
    }
}
