package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponUseHistoryCommand;
import com.flab.goodchoice.coupon.domain.CouponUseHistory;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponUseHistoryEntity;
import com.flab.goodchoice.member.infrastructure.entity.MemberEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponUseHistoryRepository;

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
