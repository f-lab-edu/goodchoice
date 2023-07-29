package com.flab.goodchoicemember.infrastructure;

import com.flab.goodchoicemember.application.MemberCommand;
import com.flab.goodchoicemember.domain.model.Member;
import com.flab.goodchoicemember.infrastructure.entity.MemberEntity;
import com.flab.goodchoicemember.infrastructure.repositories.MemberRepository;

public class FakeMemberCommand implements MemberCommand {

    private final MemberRepository memberRepository;

    public FakeMemberCommand(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member createMember(Member member) {
        MemberEntity memberEntity = MemberEntity.of(member);
        return memberRepository.save(memberEntity).toMember();
    }
}