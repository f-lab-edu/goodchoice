package com.flab.goodchoice.coupon.domain.repositories;

import com.flab.goodchoice.coupon.domain.Member;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryMemberRepository implements MemberRepository {
    private final Map<Long, Member> members = new HashMap<>();

    @Override
    public Optional<Member> findById(Long memberId) {
        return Optional.ofNullable(members.get(memberId));
    }

    @Override
    public Member save(Member member) {
        members.put(members.size() + 1L, member);
        return member;
    }
}
