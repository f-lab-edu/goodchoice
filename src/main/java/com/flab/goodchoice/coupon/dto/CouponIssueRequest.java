package com.flab.goodchoice.coupon.dto;

import java.util.UUID;

public record CouponIssueRequest(
        Long memberId,
        UUID couponToken
) {
}
