package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponCommand;
import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponRepository;

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
