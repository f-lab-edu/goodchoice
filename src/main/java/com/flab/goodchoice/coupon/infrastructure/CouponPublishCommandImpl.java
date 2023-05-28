package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponPublishCommand;
import com.flab.goodchoice.coupon.domain.CouponPublish;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponPublishEntity;
import com.flab.goodchoice.coupon.infrastructure.entity.MemberEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponPublishRepository;
import org.springframework.stereotype.Component;

@Component
public class CouponPublishCommandImpl implements CouponPublishCommand {

    private final CouponPublishRepository couponPublishRepository;

    public CouponPublishCommandImpl(CouponPublishRepository couponPublishRepository) {
        this.couponPublishRepository = couponPublishRepository;
    }

    @Override
    public CouponPublish save(CouponPublish couponPublish) {
        CouponPublishEntity couponPublishEntity = CouponPublishEntity.builder()
                .couponPublishToken(couponPublish.getCouponPublishToken())
                .memberEntity(MemberEntity.of(couponPublish.getMember()))
                .couponEntity(CouponEntity.of(couponPublish.getCoupon()))
                .usedYn(couponPublish.isUsedYn())
                .build();

        CouponPublishEntity saveCouponPublishEntity = couponPublishRepository.save(couponPublishEntity);

        return saveCouponPublishEntity.toCouponPublish();
    }

    @Override
    public void modify(CouponPublish couponPublish) {
        CouponPublishEntity couponPublishEntity = CouponPublishEntity.builder()
                .id(couponPublish.getId())
                .couponPublishToken(couponPublish.getCouponPublishToken())
                .memberEntity(MemberEntity.of(couponPublish.getMember()))
                .couponEntity(CouponEntity.of(couponPublish.getCoupon()))
                .usedYn(couponPublish.isUsedYn())
                .createdAt(couponPublish.getCreatedAt())
                .usedAt(couponPublish.getUsedAt())
                .build();

        couponPublishRepository.save(couponPublishEntity);
    }
}
