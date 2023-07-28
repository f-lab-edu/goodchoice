package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.infrastructure.FakeCouponIssueQuery;
import com.flab.goodchoicecoupon.application.CouponIssueQuery;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueRepository;

public class FakeCouponIssueRetrievalService {

    private final CouponIssueRepository couponIssueRepository;

    private CouponIssueQuery couponIssueQuery;

    FakeCouponIssueRetrievalService(CouponIssueRepository couponIssueRepository) {
        this.couponIssueRepository = couponIssueRepository;
    }

    CouponIssueRetrievalService createCouponIssueRetrievalService() {
        couponIssueQuery = new FakeCouponIssueQuery(couponIssueRepository);

        return new CouponIssueRetrievalService(couponIssueQuery);
    }
}
