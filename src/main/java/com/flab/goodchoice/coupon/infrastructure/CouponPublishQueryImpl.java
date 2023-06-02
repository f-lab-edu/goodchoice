package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponPublishQuery;
import com.flab.goodchoice.coupon.domain.CouponPublish;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponPublishEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponPublishRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

import static java.util.stream.Collectors.toList;

@Component
public class CouponPublishQueryImpl implements CouponPublishQuery {

    private final CouponPublishRepository couponPublishRepository;

    public CouponPublishQueryImpl(CouponPublishRepository couponPublishRepository) {
        this.couponPublishRepository = couponPublishRepository;
    }

    @Override
    public List<CouponPublish> findCouponHistoryFetchByMemberId(Long memberId) {
        return couponPublishRepository.findCouponHistoryFetchByMemberId(memberId).stream()
                .map(CouponPublishEntity::toCouponPublish)
                .collect(toList());
    }

    @Override
    public CouponPublish findByCouponPublishTokenAndMemberEntityId(UUID couponPublishToken, Long memberId) {
        CouponPublishEntity couponPublishEntity = couponPublishRepository.findByCouponPublishTokenAndMemberEntityId(couponPublishToken, memberId).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 보유하고 있지 않습니다."));
        return couponPublishEntity.toCouponPublish();
    }
}
