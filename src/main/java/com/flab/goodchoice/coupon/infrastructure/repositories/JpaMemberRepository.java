package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.domain.Member;
import com.flab.goodchoice.coupon.domain.repositories.MemberRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaMemberRepository extends MemberRepository, JpaRepository<Member, Long> {
}
