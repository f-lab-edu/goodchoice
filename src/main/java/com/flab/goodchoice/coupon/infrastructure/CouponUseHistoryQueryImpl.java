package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponUseHistoryQuery;
import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponUseHistory;
import com.flab.goodchoice.member.domain.model.Member;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponUseHistoryEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponUseHistoryRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Component
public class CouponUseHistoryQueryImpl implements CouponUseHistoryQuery {

    private final CouponUseHistoryRepository couponUseHistoryRepository;

    public CouponUseHistoryQueryImpl(CouponUseHistoryRepository couponUseHistoryRepository) {
        this.couponUseHistoryRepository = couponUseHistoryRepository;
    }

    @Override
    public CouponUseHistory getCouponUseHistory(Member member, Coupon coupon) {
        CouponUseHistoryEntity couponUseHistoryEntity = couponUseHistoryRepository.findByMemberIdAndCouponEntityId(member.getId(), coupon.getId()).orElseThrow();
        return couponUseHistoryEntity.toCouponUseHistory();
    }
}
