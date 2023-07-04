package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.infrastructure.FakeCouponIssueQuery;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponIssueRepository;

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
