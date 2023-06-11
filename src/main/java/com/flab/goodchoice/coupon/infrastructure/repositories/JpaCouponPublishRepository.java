package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.infrastructure.entity.CouponPublishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponPublishRepository extends CouponPublishRepository, JpaRepository<CouponPublishEntity, Long> {
}
