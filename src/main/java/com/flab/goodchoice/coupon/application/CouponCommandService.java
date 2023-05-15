package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponType;
import com.flab.goodchoice.coupon.domain.State;
import com.flab.goodchoice.coupon.domain.repositories.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
public class CouponCommandService {

    private final CouponRepository couponRepository;

    public CouponCommandService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public UUID create(final String couponName, final int stock, CouponType couponType, int discoutValue) {
        Coupon coupon = new Coupon(UUID.randomUUID(), couponName, stock, couponType, discoutValue, State.ACTIVITY);
        couponRepository.save(coupon);

        return coupon.getCouponToken();
    }

    public UUID modifyCoupon(final UUID couponToken, final String couponName, final int stock) {
        Coupon coupon = couponRepository.findByCouponToken(couponToken).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        coupon.modify(couponName, stock);

        return coupon.getCouponToken();
    }

    public UUID deleteCoupon(UUID couponToken) {
        Coupon coupon = couponRepository.findByCouponToken(couponToken).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        coupon.delete();

        return coupon.getCouponToken();
    }
}
