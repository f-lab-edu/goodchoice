package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.member.application.MemberCommand;
import com.flab.goodchoice.member.domain.model.Member;
import com.flab.goodchoice.member.infrastructure.entity.MemberEntity;
import com.flab.goodchoice.member.domain.repositories.MemberRepository;

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
