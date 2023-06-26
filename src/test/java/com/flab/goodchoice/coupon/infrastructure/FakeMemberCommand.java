package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.MemberCommand;
import com.flab.goodchoice.coupon.domain.Member;
import com.flab.goodchoice.coupon.infrastructure.entity.MemberEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.MemberRepository;

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
