package com.flab.goodchoicecoupon.infrastructure.repositories;

import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponEntityRepository extends CouponRepository, JpaRepository<CouponEntity, Long> {
}
