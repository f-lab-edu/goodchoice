package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.MemberCommand;
import com.flab.goodchoice.coupon.domain.Member;
import com.flab.goodchoice.coupon.infrastructure.entity.MemberEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.MemberRepository;
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
