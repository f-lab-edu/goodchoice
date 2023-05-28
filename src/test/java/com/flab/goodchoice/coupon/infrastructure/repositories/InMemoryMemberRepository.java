package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.infrastructure.entity.MemberEntity;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class InMemoryMemberRepository implements MemberRepository {
    private final Map<Long, MemberEntity> members = new HashMap<>();

    @Override
    public Optional<MemberEntity> findById(Long memberId) {
        return Optional.ofNullable(members.get(memberId));
    }

    @Override
    public MemberEntity save(MemberEntity member) {
        members.put(member.getId(), member);
        return member;
    }
}
