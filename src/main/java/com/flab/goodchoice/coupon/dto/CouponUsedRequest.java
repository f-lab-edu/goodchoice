package com.flab.goodchoice.coupon.dto;

import java.util.UUID;

public record CouponUsedRequest(
        Long memberId,
        UUID couponToken,
        int price
) {
}
