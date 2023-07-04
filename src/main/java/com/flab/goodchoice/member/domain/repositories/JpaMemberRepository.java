package com.flab.goodchoice.member.domain.repositories;

import com.flab.goodchoice.member.infrastructure.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends MemberRepository, JpaRepository<MemberEntity, Long> {
}
