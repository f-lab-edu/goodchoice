package com.flab.goodchoice.coupon.dto;

import java.util.UUID;

public record CouponPublishRequest(
        Long memberId,
        UUID couponToken
) {
}
