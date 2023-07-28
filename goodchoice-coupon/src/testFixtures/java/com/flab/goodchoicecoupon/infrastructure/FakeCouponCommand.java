package com.flab.goodchoicecoupon.infrastructure;

import com.flab.goodchoicecoupon.application.CouponCommand;
import com.flab.goodchoicecoupon.domain.Coupon;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponRepository;

public class FakeCouponCommand implements CouponCommand {

    private final CouponRepository couponRepository;

    public FakeCouponCommand(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Override
    public Coupon save(Coupon coupon) {
        CouponEntity couponEntity = CouponEntity.builder()
                .couponToken(coupon.getCouponToken())
                .couponName(coupon.getCouponName())
                .stock(coupon.getStock())
                .couponType(coupon.getCouponType())
                .discountValue(coupon.getDiscountValue())
                .state(coupon.getState())
                .build();

        CouponEntity saveCouponEntity = couponRepository.save(couponEntity);

        return saveCouponEntity.toCoupon();
    }

    @Override
    public void modify(Coupon coupon) {
        CouponEntity couponEntity = CouponEntity.builder()
                .id(coupon.getId())
                .couponToken(coupon.getCouponToken())
                .couponName(coupon.getCouponName())
                .stock(coupon.getStock())
                .couponType(coupon.getCouponType())
                .discountValue(coupon.getDiscountValue())
                .state(coupon.getState())
                .build();

        couponRepository.save(couponEntity);
    }


}
