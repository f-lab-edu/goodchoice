package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponUseHistoryQuery;
import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponUseHistory;
import com.flab.goodchoice.member.domain.model.Member;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponUseHistoryEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponUseHistoryRepository;

public class FakeCouponUseHistoryQuery implements CouponUseHistoryQuery {

    private final CouponUseHistoryRepository couponUseHistoryRepository;

    public FakeCouponUseHistoryQuery(CouponUseHistoryRepository couponUseHistoryRepository) {
        this.couponUseHistoryRepository = couponUseHistoryRepository;
    }

    @Override
    public CouponUseHistory getCouponUseHistory(Member member, Coupon coupon) {
        CouponUseHistoryEntity couponUseHistoryEntity = couponUseHistoryRepository.findByMemberIdAndCouponEntityId(member.getId(), coupon.getId()).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        return couponUseHistoryEntity.toCouponUseHistory();
    }
}
