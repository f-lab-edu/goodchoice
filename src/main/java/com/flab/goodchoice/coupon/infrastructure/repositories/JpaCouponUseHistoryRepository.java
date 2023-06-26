package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.infrastructure.entity.CouponUseHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponUseHistoryRepository extends CouponUseHistoryRepository, JpaRepository<CouponUseHistoryEntity, Long> {
}
