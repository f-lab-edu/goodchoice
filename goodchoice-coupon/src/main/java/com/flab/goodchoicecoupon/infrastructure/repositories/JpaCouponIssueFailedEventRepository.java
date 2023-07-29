package com.flab.goodchoicecoupon.infrastructure.repositories;

import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueFailedEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponIssueFailedEventRepository extends CouponIssueFailedEventRepository, JpaRepository<CouponIssueFailedEventEntity, Long> {
}
