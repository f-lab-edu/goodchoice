package com.flab.goodchoiceapi.coupon.infrastructure;

import com.flab.goodchoiceapi.member.application.MemberCommand;
import com.flab.goodchoicemember.domain.model.Member;
import com.flab.goodchoicemember.infrastructure.repositories.MemberRepository;
import com.flab.goodchoicemember.infrastructure.entity.MemberEntity;

public class FakeMemberCommand implements MemberCommand {

    private final MemberRepository memberRepository;

    public FakeMemberCommand(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member save(Member member) {
        MemberEntity memberEntity = MemberEntity.of(member);
        return memberRepository.save(memberEntity).toMember();
    }
}
