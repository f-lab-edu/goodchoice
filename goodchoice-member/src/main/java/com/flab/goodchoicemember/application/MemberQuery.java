package com.flab.goodchoicemember.application;

import com.flab.goodchoicemember.domain.model.Member;

public interface MemberQuery {
    Member getMember(Long memberId);
}
