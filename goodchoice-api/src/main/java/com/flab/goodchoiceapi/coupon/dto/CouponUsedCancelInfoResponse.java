package com.flab.goodchoiceapi.coupon.dto;

import java.util.UUID;

public record CouponUsedCancelInfoResponse(
        UUID couponToken,
        int prePrice,
        int resultPrice
) {
}
