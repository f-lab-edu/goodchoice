package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.infrastructure.entity.CouponIssueEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponIssueRepository extends CouponIssueRepository, JpaRepository<CouponIssueEntity, Long> {
}
