package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponUseService;
import com.flab.goodchoice.coupon.dto.CouponPublishRequest;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping()
@RestController
public class CouponIssueController {

    private final CouponUseService couponUseService;

    public CouponIssueController(CouponUseService couponUseService) {
        this.couponUseService = couponUseService;
    }

    @PostMapping("/api/coupons/publish")
    public UUID couponPublish(CouponPublishRequest couponPublishRequest) {
        return couponUseService.couponPublish(couponPublishRequest.memberId(), couponPublishRequest.couponToken());
    }
}
