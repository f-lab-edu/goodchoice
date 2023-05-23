package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.domain.CouponHistory;
import com.flab.goodchoice.coupon.domain.repositories.CouponHistoryRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponHistoryRepository extends CouponHistoryRepository, JpaRepository<CouponHistory, Long> {
}
