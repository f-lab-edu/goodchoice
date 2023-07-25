package com.flab.goodchoiceapi.coupon.infrastructure;

import com.flab.goodchoiceapi.coupon.application.CouponUseHistoryCommand;
import com.flab.goodchoicecoupon.domain.CouponUseHistory;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponUseHistoryEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponUseHistoryRepository;

public class FakeCouponUseHistoryCommand implements CouponUseHistoryCommand {

    private CouponUseHistoryRepository couponUseHistoryRepository;

    public FakeCouponUseHistoryCommand(CouponUseHistoryRepository couponUseHistoryRepository) {
        this.couponUseHistoryRepository = couponUseHistoryRepository;
    }

    @Override
    public CouponUseHistory save(CouponUseHistory couponUseHistory) {
        CouponUseHistoryEntity couponUseHistoryEntity = CouponUseHistoryEntity.builder()
                .memberId(couponUseHistory.getMemberId())
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
                .memberId(couponUseHistory.getMemberId())
                .couponEntity(CouponEntity.of(couponUseHistory.getCoupon()))
                .price(couponUseHistory.getPrice())
                .discountPrice(couponUseHistory.getDiscountPrice())
                .useState(couponUseHistory.getUseState())
                .createdAt(couponUseHistory.getCreatedAt())
                .build();

        couponUseHistoryRepository.save(couponUseHistoryEntity);
    }
}
