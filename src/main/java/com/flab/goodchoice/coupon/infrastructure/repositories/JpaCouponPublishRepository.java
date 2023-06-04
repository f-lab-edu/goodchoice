package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.infrastructure.entity.CouponPublishEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JpaCouponPublishRepository extends CouponPublishRepository, JpaRepository<CouponPublishEntity, Long> {
}