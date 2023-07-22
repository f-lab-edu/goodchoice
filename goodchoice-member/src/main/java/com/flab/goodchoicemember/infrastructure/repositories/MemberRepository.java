package com.flab.goodchoicemember.infrastructure.repositories;

import com.flab.goodchoicemember.infrastructure.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository {
    Optional<MemberEntity> findById(Long memberId);

    MemberEntity save(MemberEntity member);
}
