package com.flab.goodchoicemember.domain.repositories;

import com.flab.goodchoicemember.infrastructure.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends MemberRepository, JpaRepository<MemberEntity, Long> {
}
