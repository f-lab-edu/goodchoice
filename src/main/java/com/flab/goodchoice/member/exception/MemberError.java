package com.flab.goodchoice.member.exception;

import lombok.Getter;

@Getter
public enum MemberError {
    NOT_FOUND_MEMBER("M001", "해당 회원을 찾을 수 없습니다.");

    private final String errorCode;
    private final String message;

    MemberError(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }
}
