package com.flab.goodchoice.member.application;

import com.flab.goodchoice.member.domain.model.Member;

public interface MemberQuery {
    Member findById(Long memberId);
}
