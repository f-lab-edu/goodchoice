package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponType;
import com.flab.goodchoice.coupon.domain.State;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
public class CouponCommandService {

    private final CouponQuery couponQuery;
    private final CouponCommand couponCommand;

    public CouponCommandService(CouponQuery couponQuery, CouponCommand couponCommand) {
        this.couponQuery = couponQuery;
        this.couponCommand = couponCommand;
    }

    public UUID create(final String couponName, final int stock, CouponType couponType, int discoutValue) {
        Coupon coupon = new Coupon(UUID.randomUUID(), couponName, stock, couponType, discoutValue, State.ACTIVITY);
        couponCommand.save(coupon);

        return coupon.getCouponToken();
    }

    public UUID modifyCoupon(final UUID couponToken, final String couponName, final int stock) {
        Coupon coupon = couponQuery.findByCouponToken(couponToken);

        coupon.modify(couponName, stock);
        couponCommand.modify(coupon);

        return coupon.getCouponToken();
    }

    public UUID deleteCoupon(UUID couponToken) {
        Coupon coupon = couponQuery.findByCouponToken(couponToken);

        coupon.delete();
        couponCommand.modify(coupon);

        return coupon.getCouponToken();
    }
}
