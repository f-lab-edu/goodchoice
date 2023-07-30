package com.flab.goodchoicecoupon.infrastructure;

import com.flab.goodchoicecoupon.application.CouponIssueFailedEventQuery;
import com.flab.goodchoicecoupon.domain.CouponIssueFailedEvent;
import com.flab.goodchoicecoupon.exception.CouponError;
import com.flab.goodchoicecoupon.exception.CouponException;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueFailedEventEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueFailedEventRepository;
import org.springframework.stereotype.Component;

@Component
public class CouponIssueFailedEventQueryImpl implements CouponIssueFailedEventQuery {

    private final CouponIssueFailedEventRepository couponIssueFailedEventRepository;

    public CouponIssueFailedEventQueryImpl(CouponIssueFailedEventRepository couponIssueFailedEventRepository) {
        this.couponIssueFailedEventRepository = couponIssueFailedEventRepository;
    }

    @Override
    public CouponIssueFailedEvent getCouponIssueFailedEvent(Long couponIssueFailedEventId) {
        CouponIssueFailedEventEntity couponIssueFailedEventEntity = couponIssueFailedEventRepository.findById(couponIssueFailedEventId).orElseThrow(() -> new CouponException(CouponError.NOT_FOUND_COUPON_PUBLISH));
        return couponIssueFailedEventEntity.toCouponIssueFailedEvent();
    }
}
