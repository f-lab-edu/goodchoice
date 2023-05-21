package com.flab.goodchoice.coupon.domain.repositories;

import com.flab.goodchoice.coupon.domain.Coupon;
import org.springframework.data.jpa.repository.Lock;

import javax.persistence.LockModeType;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CouponRepository {
    Coupon save(Coupon coupon);

    List<Coupon> findAll();

    Optional<Coupon> findById(Long couponId);

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    Optional<Coupon> findByCouponToken(UUID couponToken);
}
