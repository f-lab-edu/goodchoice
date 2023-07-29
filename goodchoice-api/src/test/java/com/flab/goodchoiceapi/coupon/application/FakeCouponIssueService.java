package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.application.CouponCommand;
import com.flab.goodchoicecoupon.application.CouponIssueChecker;
import com.flab.goodchoicecoupon.application.CouponIssueCommand;
import com.flab.goodchoicecoupon.application.CouponQuery;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponCommand;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponIssueCommand;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponIssueExistChecker;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponQuery;
import com.flab.goodchoiceredis.infrastructure.AppliedUserRepository;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueRepository;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponRepository;
import com.flab.goodchoicemember.application.MemberQuery;
import com.flab.goodchoicemember.infrastructure.FakeMemberQuery;
import com.flab.goodchoicemember.infrastructure.repositories.MemberRepository;
import com.flab.goodchoiceredis.infrastructure.FakeAppliedUserRepository;

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
