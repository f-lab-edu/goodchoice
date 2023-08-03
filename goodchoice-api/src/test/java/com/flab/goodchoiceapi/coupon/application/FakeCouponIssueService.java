package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoiceapi.coupon.domain.CouponIssueService;
import com.flab.goodchoicecoupon.application.CouponCommand;
import com.flab.goodchoicecoupon.application.CouponIssueChecker;
import com.flab.goodchoicecoupon.application.CouponIssueCommand;
import com.flab.goodchoicecoupon.application.CouponQuery;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponCommand;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponIssueCommand;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponIssueExistChecker;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponQuery;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueRepository;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponRepository;

public class FakeCouponIssueService {

    private final CouponRepository couponRepository;
    private final CouponIssueRepository couponIssueRepository;

    private CouponQuery couponQuery;
    private CouponCommand couponCommand;
    private CouponIssueCommand couponIssueCommand;
    private CouponIssueChecker couponIssueExistChecker;

    FakeCouponIssueService(CouponRepository couponRepository, CouponIssueRepository couponIssueRepository) {
        this.couponRepository = couponRepository;
        this.couponIssueRepository = couponIssueRepository;
    }

    CouponIssueService createCouponIssueService() {
        couponQuery = new FakeCouponQuery(couponRepository);
        couponCommand = new FakeCouponCommand(couponRepository);
        couponIssueCommand = new FakeCouponIssueCommand(couponIssueRepository);
        couponIssueExistChecker = new FakeCouponIssueExistChecker(couponIssueRepository);

        return new CouponIssueService(couponQuery, couponCommand, couponIssueCommand);
    }
}
