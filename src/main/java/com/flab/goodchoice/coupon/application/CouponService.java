package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.State;
import com.flab.goodchoice.coupon.dto.CreateCouponRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
public class CouponService {

    private final CouponQuery couponQuery;
    private final CouponCommand couponCommand;

    public CouponService(CouponQuery couponQuery, CouponCommand couponCommand) {
        this.couponQuery = couponQuery;
        this.couponCommand = couponCommand;
    }

    public UUID createCoupon(CreateCouponRequest createCouponRequest) {
        Coupon coupon = Coupon.builder()
                .couponToken(UUID.randomUUID())
                .couponName(createCouponRequest.couponName())
                .stock(createCouponRequest.stock())
                .couponType(createCouponRequest.couponType())
                .discountValue(createCouponRequest.discountValue())
                .state(State.ACTIVITY)
                .build();

        couponCommand.save(coupon);

        return coupon.getCouponToken();
    }

    public UUID modifyCoupon(UUID couponToken, String couponName, int stock) {
        Coupon coupon = getCoupon(couponToken);

        coupon.modify(couponName, stock);
        couponCommand.modify(coupon);

        return coupon.getCouponToken();
    }

    public UUID removeCoupon(UUID couponToken) {
        Coupon coupon = getCoupon(couponToken);

        coupon.delete();
        couponCommand.modify(coupon);

        return coupon.getCouponToken();
    }

    private Coupon getCoupon(UUID couponToken) {
        return couponQuery.getCoupon(couponToken);
    }
}
