package com.flab.goodchoicemember.infrastructure;

import com.flab.goodchoicemember.application.MemberCommand;
import com.flab.goodchoicemember.domain.model.Member;
import com.flab.goodchoicemember.infrastructure.repositories.MemberRepository;
import com.flab.goodchoicemember.infrastructure.entity.MemberEntity;
import org.springframework.stereotype.Component;

@Component
public class MemberCommandImpl implements MemberCommand {

    private MemberRepository memberRepository;

    public MemberCommandImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member createMember(Member member) {
        MemberEntity memberEntity = MemberEntity.builder().build();
        return memberRepository.save(memberEntity).toMember();
    }
}
