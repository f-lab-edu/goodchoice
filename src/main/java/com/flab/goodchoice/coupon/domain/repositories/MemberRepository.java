package com.flab.goodchoice.coupon.domain.repositories;

import com.flab.goodchoice.coupon.domain.Member;

import java.util.Optional;

public interface MemberRepository {
    Optional<Member> findById(Long memberId);
}
