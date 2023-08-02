package com.flab.goodchoicecoupon.infrastructure.repositories;

import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueFailedEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponIssueFailedRepository extends CouponIssueFailedRepository, JpaRepository<CouponIssueFailedEntity, Long> {
}
