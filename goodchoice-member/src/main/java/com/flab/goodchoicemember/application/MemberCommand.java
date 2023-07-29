package com.flab.goodchoicemember.application;

import com.flab.goodchoicemember.domain.model.Member;

public interface MemberCommand {
    Member createMember(Member member);
}
