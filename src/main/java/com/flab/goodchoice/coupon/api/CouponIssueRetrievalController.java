package com.flab.goodchoice.coupon.api;

import com.flab.goodchoice.coupon.application.CouponIssueRetrievalService;
import com.flab.goodchoice.coupon.dto.MemberSpecificCouponResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping("/api/coupons")
@RestController
public class CouponIssueRetrievalController {

    private final CouponIssueRetrievalService couponIssueRetrievalService;

    public CouponIssueRetrievalController(CouponIssueRetrievalService couponIssueRetrievalService) {
        this.couponIssueRetrievalService = couponIssueRetrievalService;
    }

    @GetMapping("/issue/{memberId}")
    public List<MemberSpecificCouponResponse> getMemberCoupon(@PathVariable Long memberId) {
        return couponIssueRetrievalService.getMemberCoupon(memberId);
    }
}
