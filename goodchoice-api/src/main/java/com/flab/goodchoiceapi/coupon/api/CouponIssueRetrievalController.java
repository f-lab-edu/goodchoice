package com.flab.goodchoiceapi.coupon.api;

import com.flab.goodchoiceapi.coupon.application.CouponIssueRetrievalService;
import com.flab.goodchoiceapi.coupon.dto.MemberSpecificCouponResponse;
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
    public List<MemberSpecificCouponResponse> getIssuedMemberCoupon(@PathVariable Long memberId) {
        return couponIssueRetrievalService.getIssuedMemberCoupon(memberId);
    }
}
