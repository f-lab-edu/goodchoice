package com.flab.goodchoicecoupon.infrastructure;

import com.flab.goodchoicecoupon.application.CouponIssueFailedQuery;
import com.flab.goodchoicecoupon.domain.CouponIssueFailed;
import com.flab.goodchoicecoupon.exception.CouponError;
import com.flab.goodchoicecoupon.exception.CouponException;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueFailedEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueFailedRepository;
import org.springframework.stereotype.Component;

@Component
public class CouponIssueFailedQueryImpl implements CouponIssueFailedQuery {

    private final CouponIssueFailedRepository couponIssueFailedEventRepository;

    public CouponIssueFailedQueryImpl(CouponIssueFailedRepository couponIssueFailedEventRepository) {
        this.couponIssueFailedEventRepository = couponIssueFailedEventRepository;
    }

    @Override
    public CouponIssueFailed getCouponIssueFailedEvent(Long couponIssueFailedEventId) {
        CouponIssueFailedEntity couponIssueFailedEventEntity = couponIssueFailedEventRepository.findById(couponIssueFailedEventId).orElseThrow(() -> new CouponException(CouponError.NOT_FOUND_COUPON_PUBLISH));
        return couponIssueFailedEventEntity.toCouponIssueFailedEvent();
    }
}
