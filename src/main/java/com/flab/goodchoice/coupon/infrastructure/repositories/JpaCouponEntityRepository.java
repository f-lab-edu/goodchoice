package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.infrastructure.entity.CouponEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponEntityRepository extends CouponRepository, JpaRepository<CouponEntity, Long> {
}
