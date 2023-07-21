package com.flab.goodchoicecoupon.infrastructure.repositories;

import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponIssueRepository extends CouponIssueRepository, JpaRepository<CouponIssueEntity, Long> {
}
