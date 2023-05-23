package com.flab.goodchoice.coupon.dto;

import com.flab.goodchoice.coupon.domain.State;

import java.util.UUID;

public record CouponInfoResponse(
        UUID couponToken,
        String couponName,
        int stock,
        State state
) {

}
