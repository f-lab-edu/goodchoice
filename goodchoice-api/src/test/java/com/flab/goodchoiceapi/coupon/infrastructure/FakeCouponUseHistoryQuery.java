package com.flab.goodchoiceapi.coupon.infrastructure;

import com.flab.goodchoiceapi.coupon.application.CouponUseHistoryQuery;
import com.flab.goodchoicecoupon.domain.Coupon;
import com.flab.goodchoicecoupon.domain.CouponUseHistory;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponUseHistoryEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponUseHistoryRepository;
import com.flab.goodchoicemember.domain.model.Member;

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
