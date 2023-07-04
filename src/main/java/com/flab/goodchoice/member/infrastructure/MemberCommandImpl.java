package com.flab.goodchoice.member.infrastructure;

import com.flab.goodchoice.member.application.MemberCommand;
import com.flab.goodchoice.member.domain.model.Member;
import com.flab.goodchoice.member.infrastructure.entity.MemberEntity;
import com.flab.goodchoice.member.domain.repositories.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class MemberCommandImpl implements MemberCommand {

    private MemberRepository memberRepository;

    public MemberCommandImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member save(Member member) {
        MemberEntity memberEntity = MemberEntity.builder().build();
        return memberRepository.save(memberEntity).toMember();
    }
}
