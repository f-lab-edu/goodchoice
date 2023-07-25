package com.flab.goodchoiceapi.member.application;

import com.flab.goodchoicemember.domain.model.Member;

public interface MemberQuery {
    Member findById(Long memberId);
}
