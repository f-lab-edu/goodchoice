package com.flab.goodchoice.coupon.domain.repositories;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponPublish;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CouponPublishRepository {
    CouponPublish save(CouponPublish couponPublishHistory);

    int countByCoupon(Coupon coupon);

    @Query("select cph from CouponPublish cph join fetch cph.coupon")
    List<CouponPublish> findCouponHistoryFetchByMemberId(Long memberId);

    Optional<CouponPublish> findByCouponAndMemberId(Coupon coupon, Long memberId);
}
