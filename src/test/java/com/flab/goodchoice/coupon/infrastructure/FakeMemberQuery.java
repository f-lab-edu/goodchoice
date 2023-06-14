package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.member.application.MemberQuery;
import com.flab.goodchoice.member.domain.model.Member;
import com.flab.goodchoice.member.exception.MemberError;
import com.flab.goodchoice.member.exception.MemberException;
import com.flab.goodchoice.member.infrastructure.entity.MemberEntity;
import com.flab.goodchoice.member.domain.repositories.MemberRepository;

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
