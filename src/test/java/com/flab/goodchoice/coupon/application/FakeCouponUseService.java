package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.infrastructure.*;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponIssueRepository;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponRepository;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponUseHistoryRepository;
import com.flab.goodchoice.member.application.MemberQuery;
import com.flab.goodchoice.member.domain.repositories.MemberRepository;

public class FakeCouponUseService {

    private final MemberRepository memberRepository;
    private final CouponIssueRepository couponIssueRepository;
    private final CouponUseHistoryRepository couponUseHistoryEntityRepository;

    private MemberQuery memberQuery;
    private CouponIssueQuery couponIssueQuery;
    private CouponIssueCommand couponIssueCommand;
    private CouponUseHistoryQuery couponUseHistoryQuery;
    private CouponUseHistoryCommand couponUseHistoryCommand;

    public FakeCouponUseService(MemberRepository memberRepository, CouponIssueRepository couponIssueRepository, CouponUseHistoryRepository couponUseHistoryEntityRepository) {
        this.memberRepository = memberRepository;
        this.couponIssueRepository = couponIssueRepository;
        this.couponUseHistoryEntityRepository = couponUseHistoryEntityRepository;
    }

    CouponUseService createCouponUseService() {
        memberQuery = new FakeMemberQuery(memberRepository);
        couponIssueQuery = new FakeCouponIssueQuery(couponIssueRepository);
        couponIssueCommand = new FakeCouponIssueCommand(couponIssueRepository);
        couponUseHistoryQuery = new FakeCouponUseHistoryQuery(couponUseHistoryEntityRepository);
        couponUseHistoryCommand = new FakeCouponUseHistoryCommand(couponUseHistoryEntityRepository);

        return new CouponUseService(memberQuery, couponIssueQuery, couponIssueCommand, couponUseHistoryQuery, couponUseHistoryCommand);
    }
}
