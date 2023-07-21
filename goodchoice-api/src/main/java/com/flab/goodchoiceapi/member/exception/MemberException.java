package com.flab.goodchoiceapi.member.exception;

import com.flab.goodchoiceapi.common.exception.BaseException;

public class MemberException extends BaseException {

    public MemberException(String errorCode, String message) {
        super(errorCode, message);
    }

    public MemberException(MemberError memberError) {
        this(memberError.getErrorCode(), memberError.getMessage());
    }
}
