package com.flab.goodchoice.member.domain.repositories;

import com.flab.goodchoice.member.infrastructure.entity.MemberEntity;

import java.util.Optional;

public interface MemberRepository {
    Optional<MemberEntity> findById(Long memberId);

    MemberEntity save(MemberEntity member);
}
