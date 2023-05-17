package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.domain.CouponPublishHistory;
import com.flab.goodchoice.coupon.domain.repositories.CouponHistoryPublishRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponHistoryRepository extends CouponHistoryPublishRepository, JpaRepository<CouponPublishHistory, Long> {
}
