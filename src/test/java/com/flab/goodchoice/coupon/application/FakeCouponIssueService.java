package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.infrastructure.*;
import com.flab.goodchoice.coupon.infrastructure.repositories.*;
import com.flab.goodchoice.member.application.MemberQuery;
import com.flab.goodchoice.member.domain.repositories.MemberRepository;

public class FakeCouponIssueService {

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final CouponIssueRepository couponIssueRepository;

    private MemberQuery memberQuery;
    private CouponQuery couponQuery;
    private CouponCommand couponCommand;
    private CouponIssueCommand couponIssueCommand;
    private AppliedUserRepository appliedUserRepository;
    private CouponIssueChecker couponIssueExistChecker;

    FakeCouponIssueService(MemberRepository memberRepository, CouponRepository couponRepository, CouponIssueRepository couponIssueRepository) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
        this.couponIssueRepository = couponIssueRepository;
    }

    CouponIssueService createCouponIssueService() {
        memberQuery = new FakeMemberQuery(memberRepository);
        couponQuery = new FakeCouponQuery(couponRepository);
        couponCommand = new FakeCouponCommand(couponRepository);
        couponIssueCommand = new FakeCouponIssueCommand(couponIssueRepository);
        appliedUserRepository = new FakeAppliedUserRepository();
        couponIssueExistChecker = new FakeCouponIssueExistChecker(couponIssueRepository);

        return new CouponIssueService(memberQuery, couponQuery, couponCommand, couponIssueCommand, couponIssueExistChecker,  appliedUserRepository);
    }
}
