package com.flab.goodchoiceapi.coupon.api;

import com.flab.goodchoiceapi.coupon.application.CouponIssueService;
import com.flab.goodchoiceapi.coupon.dto.CouponIssueRequest;
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

    @PostMapping("/issue")
    public UUID createCouponIssue(@RequestBody CouponIssueRequest couponIssueRequest) {
        return couponIssueService.couponIssue(couponIssueRequest.memberId(), couponIssueRequest.couponToken());
    }

    @PostMapping("/issue/redisson")
    public UUID createCouponIssueRedissonAop(@RequestBody CouponIssueRequest couponIssueRequest) {
        return couponIssueService.couponIssueRedissonAop(couponIssueRequest.memberId(), couponIssueRequest.couponToken());
    }
}
