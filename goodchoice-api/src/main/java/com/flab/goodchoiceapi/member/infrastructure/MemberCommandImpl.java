package com.flab.goodchoiceapi.member.infrastructure;

import com.flab.goodchoiceapi.member.application.MemberCommand;
import com.flab.goodchoicemember.domain.model.Member;
import com.flab.goodchoicemember.domain.repositories.MemberRepository;
import com.flab.goodchoicemember.infrastructure.entity.MemberEntity;
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
