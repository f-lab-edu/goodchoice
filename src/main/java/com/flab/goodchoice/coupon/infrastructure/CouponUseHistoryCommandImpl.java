package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponUseHistoryCommand;
import com.flab.goodchoice.coupon.domain.CouponUseHistory;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponUseHistoryEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponUseHistoryRepository;
import org.springframework.stereotype.Component;

@Component
public class CouponUseHistoryCommandImpl implements CouponUseHistoryCommand {

    private final CouponUseHistoryRepository couponUseHistoryRepository;

    public CouponUseHistoryCommandImpl(CouponUseHistoryRepository couponUseHistoryRepository) {
        this.couponUseHistoryRepository = couponUseHistoryRepository;
    }

    @Override
    public CouponUseHistoryEntity save(CouponUseHistory couponUseHistory) {
        CouponUseHistoryEntity couponUseHistoryEntity = CouponUseHistoryEntity.builder()
                .member(couponUseHistory.getMember())
                .couponEntity(couponUseHistory.getCouponEntity())
                .price(couponUseHistory.getPrice())
                .discountPrice(couponUseHistory.getDiscountPrice())
                .useState(couponUseHistory.getUseState())
                .build();

        return couponUseHistoryRepository.save(couponUseHistoryEntity);
    }
}
