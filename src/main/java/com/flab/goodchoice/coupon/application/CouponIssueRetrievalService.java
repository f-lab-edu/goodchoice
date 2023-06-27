package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.CouponPublish;
import com.flab.goodchoice.coupon.dto.MemberSpecificCouponResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class CouponIssueRetrievalService {

    private final CouponPublishQuery couponPublishQuery;

    public CouponIssueRetrievalService(CouponPublishQuery couponPublishQuery) {
        this.couponPublishQuery = couponPublishQuery;
    }

    public List<MemberSpecificCouponResponse> getMemberCoupon(Long memberId) {
        List<CouponPublish> couponPublishes = couponPublishQuery.getCouponIssue(memberId);
        return couponPublishes.stream()
                .map(couponPublish -> MemberSpecificCouponResponse.of(couponPublish.getCoupon()))
                .toList();
    }
}
