package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.domain.CouponPublish;
import com.flab.goodchoice.coupon.domain.repositories.CouponPublishRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponPublishRepository extends CouponPublishRepository, JpaRepository<CouponPublish, Long> {
}
