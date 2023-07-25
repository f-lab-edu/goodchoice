package com.flab.goodchoicecoupon.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponCommonResponse<T> {

    private String errorCode;
    private String message;
    private T data;

    private CouponCommonResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    private CouponCommonResponse(String errorCode, String message, T data) {
        this(errorCode, message);
        this.data = data;
    }

    public static <T> CouponCommonResponse<T> success(String errorCode, String message, T data) {
        return new CouponCommonResponse<>(errorCode, message, data);
    }

    public static CouponCommonResponse<Void> fail(String errorCode, String message) {
        return new CouponCommonResponse<>(errorCode, message);
    }
}
