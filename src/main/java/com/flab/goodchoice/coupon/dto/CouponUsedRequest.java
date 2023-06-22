package com.flab.goodchoice.coupon.dto;

import javax.validation.constraints.Min;
import java.util.UUID;

public record CouponUsedRequest(
        Long memberId,
        UUID couponPublishToken,
        @Min(value = 0, message = "가격은 음수가 될수 없습니다.")
        int price
) {
}
