package com.flab.goodchoicecoupon.infrastructure;

import com.flab.goodchoicecoupon.application.CouponIssueFailedEventCommand;
import com.flab.goodchoicecoupon.domain.CouponIssueFailedEvent;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueFailedEventEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueFailedEventRepository;

public class FakeCouponIssueFailedEventCommand implements CouponIssueFailedEventCommand {

    private final CouponIssueFailedEventRepository couponIssueFailedEventRepository;

    public FakeCouponIssueFailedEventCommand(CouponIssueFailedEventRepository couponIssueFailedEventRepository) {
        this.couponIssueFailedEventRepository = couponIssueFailedEventRepository;
    }

    @Override
    public CouponIssueFailedEvent save(CouponIssueFailedEvent couponIssueFailedEvent) {
        CouponIssueFailedEventEntity couponIssueFailedEventEntity = CouponIssueFailedEventEntity.builder()
                .memberId(couponIssueFailedEvent.getMemberId())
                .couponToken(couponIssueFailedEvent.getCouponToken())
                .restoredYn(couponIssueFailedEvent.isRestoredYn())
                .createdAt(couponIssueFailedEvent.getCreatedAt())
                .updatedAt(couponIssueFailedEvent.getUpdatedAt())
                .build();

        couponIssueFailedEventRepository.save(couponIssueFailedEventEntity);

        return couponIssueFailedEventEntity.toCouponIssueFailedEvent();
    }
}
