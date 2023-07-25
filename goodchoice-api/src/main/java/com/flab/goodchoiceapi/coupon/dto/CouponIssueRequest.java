package com.flab.goodchoiceapi.coupon.dto;

import java.util.UUID;

public record CouponIssueRequest(
        Long memberId,
        UUID couponToken
) {
}
