package com.flab.goodchoicemember.infrastructure;

import com.flab.goodchoicemember.application.MemberQuery;
import com.flab.goodchoicemember.exception.MemberError;
import com.flab.goodchoicemember.exception.MemberException;
import com.flab.goodchoicemember.domain.model.Member;
import com.flab.goodchoicemember.infrastructure.repositories.MemberRepository;
import com.flab.goodchoicemember.infrastructure.entity.MemberEntity;

public class FakeMemberQuery implements MemberQuery {

    private final MemberRepository memberRepository;

    public FakeMemberQuery(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member getMember(Long memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberError.NOT_FOUND_MEMBER));
        return memberEntity.toMember();
    }
}
