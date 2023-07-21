package com.flab.goodchoicecoupon.infrastructure.repositories;

import com.flab.goodchoicecoupon.infrastructure.entity.CouponUseHistoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponUseHistoryRepository extends CouponUseHistoryRepository, JpaRepository<CouponUseHistoryEntity, Long> {
}
