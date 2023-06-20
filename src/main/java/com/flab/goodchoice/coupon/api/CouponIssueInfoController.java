package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponIssueInfoService;
import com.flab.goodchoice.coupon.dto.MemberSpecificCouponResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/coupons")
@RestController
public class CouponIssueInfoController {

    private final CouponIssueInfoService couponIssueInfoService;

    public CouponIssueInfoController(CouponIssueInfoService couponIssueInfoService) {
        this.couponIssueInfoService = couponIssueInfoService;
    }

    @GetMapping("/publish/{memberId}")
    public List<MemberSpecificCouponResponse> getMemberCoupon(@PathVariable Long memberId) {
        return couponIssueInfoService.getMemberCoupon(memberId);
    }
}
