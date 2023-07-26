package com.flab.goodchoicemember.application;

import com.flab.goodchoicemember.domain.model.Member;

public interface MemberRetrievalService {

    Member getMember(Long memberId);
}
