package com.flab.goodchoicecoupon.infrastructure.repositories;

import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponRepository {
    CouponEntity save(CouponEntity coupon);

    List<CouponEntity> findAll();

    Optional<CouponEntity> findById(Long couponId);

    Optional<CouponEntity> findByCouponToken(UUID couponToken);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select c from CouponEntity c where c.couponToken = ?1")
    Optional<CouponEntity> findLockByCouponToken(UUID couponToken);
}
