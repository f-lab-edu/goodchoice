package com.flab.goodchoicecoupon.infrastructure;

import com.flab.goodchoicecoupon.application.CouponIssueFailedCommand;
import com.flab.goodchoicecoupon.domain.CouponIssueFailed;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueFailedEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueFailedRepository;
import org.springframework.stereotype.Component;

@Component
public class CouponIssueFailedCommandImpl implements CouponIssueFailedCommand {

    private final CouponIssueFailedRepository couponIssueFailedEventRepository;

    public CouponIssueFailedCommandImpl(CouponIssueFailedRepository couponIssueFailedEventRepository) {
        this.couponIssueFailedEventRepository = couponIssueFailedEventRepository;
    }

    @Override
    public CouponIssueFailed save(CouponIssueFailed couponIssueFailedEvent) {
        CouponIssueFailedEntity couponIssueFailedEventEntity = CouponIssueFailedEntity.builder()
                .memberId(couponIssueFailedEvent.getMemberId())
                .couponId(couponIssueFailedEvent.getCouponId())
                .restoredYn(couponIssueFailedEvent.getRestoredYn().isRestoredYn())
                .createdAt(couponIssueFailedEvent.getCreatedAt())
                .updatedAt(couponIssueFailedEvent.getUpdatedAt())
                .build();

        couponIssueFailedEventRepository.save(couponIssueFailedEventEntity);

        return couponIssueFailedEventEntity.toCouponIssueFailedEvent();
    }
}
