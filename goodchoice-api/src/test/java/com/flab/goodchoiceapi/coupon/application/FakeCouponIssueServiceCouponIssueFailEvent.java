package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.application.*;
import com.flab.goodchoicecoupon.infrastructure.*;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueFailedEventRepository;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueRepository;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponRepository;
import com.flab.goodchoicemember.application.MemberQuery;
import com.flab.goodchoicemember.infrastructure.FakeMemberQuery;
import com.flab.goodchoicemember.infrastructure.repositories.MemberRepository;

public class FakeCouponIssueServiceCouponIssueFailEvent {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final CouponIssueRepository couponIssueRepository;
    private final CouponIssueFailedEventRepository couponIssueFailedEventRepository;

    private MemberQuery memberQuery;
    private CouponQuery couponQuery;
    private CouponCommand couponCommand;
    private CouponIssueCommand couponIssueCommand;
    private CouponIssueChecker couponIssueExistChecker;
    private CouponIssueFailedEventCommand couponIssueFailedEventCommand;

    FakeCouponIssueServiceCouponIssueFailEvent(MemberRepository memberRepository, CouponRepository couponRepository, CouponIssueRepository couponIssueRepository, CouponIssueFailedEventRepository couponIssueFailedEventRepository) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
        this.couponIssueRepository = couponIssueRepository;
        this.couponIssueFailedEventRepository = couponIssueFailedEventRepository;
    }

    CouponIssueService createCouponIssueService() {
        memberQuery = new FakeMemberQuery(memberRepository);
        couponQuery = new FakeCouponQuery(couponRepository);
        couponCommand = new FakeErrorCouponCommand(couponRepository);
        couponIssueCommand = new FakeErrorCouponIssueCommand();
        couponIssueExistChecker = new FakeCouponIssueExistChecker(couponIssueRepository);
        couponIssueFailedEventCommand = new FakeCouponIssueFailedEventCommand(couponIssueFailedEventRepository);

        return new CouponIssueService(memberQuery, couponQuery, couponCommand, couponIssueCommand, couponIssueExistChecker, couponIssueFailedEventCommand);
    }
}
