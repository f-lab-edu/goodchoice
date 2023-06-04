package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.MemberQuery;
import com.flab.goodchoice.coupon.domain.Member;
import com.flab.goodchoice.coupon.infrastructure.entity.MemberEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.MemberRepository;
import org.springframework.stereotype.Component;

@Component
public class MemberQueryImpl implements MemberQuery {

    private final MemberRepository memberRepository;

    public MemberQueryImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    public Member findById(Long memberId) {
        MemberEntity memberEntity = memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
        return memberEntity.toMember();
    }
}