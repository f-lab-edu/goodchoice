package com.flab.goodchoice.coupon.dto;

import java.util.UUID;

public record CouponUsedRequest(
        UUID couponToken,
        int price
) {
}
