package com.flab.goodchoiceapi.coupon.infrastructure;

import com.flab.goodchoiceapi.member.application.MemberQuery;
import com.flab.goodchoiceapi.member.exception.MemberError;
import com.flab.goodchoiceapi.member.exception.MemberException;
import com.flab.goodchoicemember.domain.model.Member;
import com.flab.goodchoicemember.infrastructure.repositories.MemberRepository;
import com.flab.goodchoicemember.infrastructure.entity.MemberEntity;

public class FakeMemberQuery implements MemberQuery {

    private final MemberRepository memberRepository;

    public FakeMemberQuery(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member findById(Long memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(() -> new MemberException(MemberError.NOT_FOUND_MEMBER));
        return memberEntity.toMember();
    }
}
