package com.flab.goodchoicecoupon.exception;

import lombok.Getter;

@Getter
public enum CouponError {
    NOT_FOUND_COUPON_PUBLISH("C001", "해당 쿠폰을 보유하고 있지 않습니다."),
    NOT_FOUND_COUPON("C002", "해당 쿠폰을 찾을 수 없습니다."),
    NOT_DUPLICATION_COUPON("C003", "한계정당 하나의 쿠폰을 가질 수 있습니다."),
    EMPTY_COUPON_NAME("C004", "빈 쿠폰명으로 수정할 수 없습니다."),
    NEGATIVE_COUPON_COUNT("C005", "쿠폰 갯수는 0보다 작을 수 없습니다."),
    ALL_CREATED_COUPON("C006", "선택된 쿠폰이 모두 소진 되었습니다.");

    private final String errorCode;
    private final String message;

    CouponError(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
