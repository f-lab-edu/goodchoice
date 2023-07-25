package com.flab.goodchoiceapi.coupon.infrastructure;

import com.flab.goodchoiceapi.coupon.application.CouponCommand;
import com.flab.goodchoicecoupon.domain.Coupon;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponRepository;
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
