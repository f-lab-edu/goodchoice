package com.flab.goodchoice.coupon.dto;

import java.util.UUID;

public record CouponUsedInfoResponse(
        UUID couponToken,
        int prePrice,
        int discountPrice,
        int resultPrice
) {
}
