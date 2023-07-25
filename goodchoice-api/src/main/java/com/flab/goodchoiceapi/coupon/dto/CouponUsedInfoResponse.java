package com.flab.goodchoiceapi.coupon.dto;

import java.util.UUID;

public record CouponUsedInfoResponse(
        UUID couponToken,
        int prePrice,
        int discountPrice,
        int resultPrice
) {
}
