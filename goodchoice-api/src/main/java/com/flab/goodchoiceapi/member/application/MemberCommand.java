package com.flab.goodchoiceapi.member.application;

import com.flab.goodchoicemember.domain.model.Member;

public interface MemberCommand {
    Member save(Member member);
}
