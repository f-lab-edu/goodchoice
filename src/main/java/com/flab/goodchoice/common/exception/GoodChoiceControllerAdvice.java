package com.flab.goodchoice.common.exception;

import com.flab.goodchoice.common.response.GoodChoiceCommonResponse;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GoodChoiceControllerAdvice {

    @ExceptionHandler(BaseException.class)
    public GoodChoiceCommonResponse<Void> onException(BaseException baseException) {
        return GoodChoiceCommonResponse.fail(baseException.getErrorCode(), baseException.getMessage());
    }
}
