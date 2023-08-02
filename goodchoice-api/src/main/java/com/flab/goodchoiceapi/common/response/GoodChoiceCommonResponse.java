package com.flab.goodchoiceapi.common.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GoodChoiceCommonResponse<T> {

    private String code;
    private String message;
    private T data;

    private GoodChoiceCommonResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    private GoodChoiceCommonResponse(String code, String message, T data) {
        this(code, message);
        this.data = data;
    }

    public static <T> GoodChoiceCommonResponse<T> success(String code, String message, T data) {
        return new GoodChoiceCommonResponse<>(code, message, data);
    }

    public static GoodChoiceCommonResponse<Void> success(String message) {
        return new GoodChoiceCommonResponse<>("200", message);
    }

    public static GoodChoiceCommonResponse<Void> fail(String code, String message) {
        return new GoodChoiceCommonResponse<>(code, message);
    }
}
