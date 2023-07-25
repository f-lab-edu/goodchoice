package com.flab.goodchoiceapi.coupon.infrastructure;

import com.flab.goodchoiceapi.coupon.application.CouponUseHistoryQuery;
import com.flab.goodchoicecoupon.domain.Coupon;
import com.flab.goodchoicecoupon.domain.CouponUseHistory;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponUseHistoryEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponUseHistoryRepository;
import com.flab.goodchoicemember.domain.model.Member;
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
