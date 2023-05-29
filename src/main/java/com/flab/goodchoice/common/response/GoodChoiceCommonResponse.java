package com.flab.goodchoice.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoodChoiceCommonResponse<T> {

    private String errorCode;
    private String message;
    private T data;

    private GoodChoiceCommonResponse(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    private GoodChoiceCommonResponse(String errorCode, String message, T data) {
        this(errorCode, message);
        this.data = data;
    }

    public static <T> GoodChoiceCommonResponse<T> success(String errorCode, String message, T data) {
        return new GoodChoiceCommonResponse<>(errorCode, message, data);
    }

    public static GoodChoiceCommonResponse<Void> fail(String errorCode, String message) {
        return new GoodChoiceCommonResponse<>(errorCode, message);
    }
}
