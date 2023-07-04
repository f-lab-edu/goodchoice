package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponCommand;
import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class CouponCommandImpl implements CouponCommand {

    private final CouponRepository couponRepository;

    public CouponCommandImpl(CouponRepository couponRepository) {
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

        couponRepository.save(couponEntity);

        return couponEntity.toCoupon();
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
                .createdAt(coupon.getCreatedAt())
                .updatedAt(coupon.getUpdatedAt())
                .build();

        couponRepository.save(couponEntity);
    }
}
