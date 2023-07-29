package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.application.CouponIssueCommand;
import com.flab.goodchoicecoupon.application.CouponIssueQuery;
import com.flab.goodchoicecoupon.application.CouponUseHistoryCommand;
import com.flab.goodchoicecoupon.application.CouponUseHistoryQuery;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponIssueCommand;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponIssueQuery;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponUseHistoryCommand;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponUseHistoryQuery;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueRepository;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponUseHistoryRepository;
import com.flab.goodchoicemember.application.MemberQuery;
import com.flab.goodchoicemember.infrastructure.FakeMemberQuery;
import com.flab.goodchoicemember.infrastructure.repositories.MemberRepository;

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
