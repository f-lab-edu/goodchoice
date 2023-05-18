package com.flab.goodchoice.coupon.domain.repositories;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponPublishHistory;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CouponHistoryPublishRepository {
    CouponPublishHistory save(CouponPublishHistory couponPublishHistory);

    int countByCoupon(Coupon coupon);

    @Query("select cph from CouponPublishHistory cph join fetch cph.coupon")
    List<CouponPublishHistory> findCouponHistoryFetchByMemberId(Long memberId);
}
