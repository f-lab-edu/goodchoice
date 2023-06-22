package com.flab.goodchoice.coupon.dto;

import javax.validation.constraints.Min;

public record ModifyCouponRequest(
        String couponName,
        @Min(value = 0, message = "쿠폰 갯수는 음수가 될수 없습니다.")
        int stock
) {
}
