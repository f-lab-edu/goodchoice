package com.flab.goodchoicecoupon.infrastructure;

import com.flab.goodchoicecoupon.application.CouponUseHistoryCommand;
import com.flab.goodchoicecoupon.domain.CouponUseHistory;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponUseHistoryEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponUseHistoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class CouponUseHistoryCommandImpl implements CouponUseHistoryCommand {

    private final CouponUseHistoryRepository couponUseHistoryRepository;

    public CouponUseHistoryCommandImpl(CouponUseHistoryRepository couponUseHistoryRepository) {
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
