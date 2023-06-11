package com.flab.goodchoice.coupon.dto;


import com.flab.goodchoice.coupon.domain.CouponType;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public record CreateCouponRequest(
        @NotBlank(message = "쿠폰명을 입력해주세요.")
        String couponName,

        @Min(value = 0, message = "쿠폰 갯수는 음수가 될수 없습니다.")
        int stock,
        @NotNull(message = "쿠폰 종류를 입력해주세요.")
        CouponType couponType,
        @Min(value = 1, message = "쿠폰 할인율을 입력해주세요.")
        int discountValue
) {
}
