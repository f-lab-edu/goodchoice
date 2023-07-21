package com.flab.goodchoiceapi.coupon.infrastructure;

import com.flab.goodchoiceapi.coupon.application.CouponUseHistoryCommand;
import com.flab.goodchoicecoupon.domain.CouponUseHistory;
import com.flab.goodchoiceapi.coupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoiceapi.coupon.infrastructure.entity.CouponUseHistoryEntity;
import com.flab.goodchoiceapi.coupon.infrastructure.repositories.CouponUseHistoryRepository;
import com.flab.goodchoiceapi.member.infrastructure.entity.MemberEntity;

public class FakeCouponUseHistoryCommand implements CouponUseHistoryCommand {

    private CouponUseHistoryRepository couponUseHistoryRepository;

    public FakeCouponUseHistoryCommand(CouponUseHistoryRepository couponUseHistoryRepository) {
        this.couponUseHistoryRepository = couponUseHistoryRepository;
    }

    @Override
    public CouponUseHistory save(CouponUseHistory couponUseHistory) {
        CouponUseHistoryEntity couponUseHistoryEntity = CouponUseHistoryEntity.builder()
                .member(MemberEntity.of(couponUseHistory.getMember()))
                .couponEntity(CouponEntity.of(couponUseHistory.getCoupon()))
                .price(couponUseHistory.getPrice())
                .discountPrice(couponUseHistory.getDiscountPrice())
                .useState(couponUseHistory.getUseState())
                .build();

        return couponUseHistoryRepository.save(couponUseHistoryEntity).toCouponUseHistory();
    }

    @Override
    public void modify(CouponUseHistory couponUseHistory) {
        CouponUseHistoryEntity couponUseHistoryEntity = CouponUseHistoryEntity.builder()
                .id(couponUseHistory.getId())
                .member(MemberEntity.of(couponUseHistory.getMember()))
                .couponEntity(CouponEntity.of(couponUseHistory.getCoupon()))
                .price(couponUseHistory.getPrice())
                .discountPrice(couponUseHistory.getDiscountPrice())
                .useState(couponUseHistory.getUseState())
                .createdAt(couponUseHistory.getCreatedAt())
                .build();

        couponUseHistoryRepository.save(couponUseHistoryEntity);
    }
}
