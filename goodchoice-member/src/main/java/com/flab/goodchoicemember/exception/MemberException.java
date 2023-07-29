package com.flab.goodchoicemember.exception;

public class MemberException extends RuntimeException {

    private String errorCode;
    private String message;

    public MemberException(String errorCode, String message) {
        this.errorCode = errorCode;
        this.message = message;
    }

    public MemberException(MemberError memberError) {
        this(memberError.getErrorCode(), memberError.getMessage());
    }
}
