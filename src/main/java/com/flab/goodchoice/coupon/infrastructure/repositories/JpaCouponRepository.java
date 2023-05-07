package com.flab.goodchoice.coupon.infrastructure.repositories;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.repositories.CouponRepository;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaCouponRepository extends CouponRepository, JpaRepository<Coupon, Long> {

}
