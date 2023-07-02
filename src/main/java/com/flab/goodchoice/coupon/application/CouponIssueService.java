package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.common.aop.LimitedCountLock;
import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponIssue;
import com.flab.goodchoice.coupon.infrastructure.repositories.AppliedUserRepository;
import com.flab.goodchoice.member.application.MemberQuery;
import com.flab.goodchoice.member.domain.model.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
public class CouponIssueService {

    private final MemberQuery memberQuery;
    private final CouponQuery couponQuery;
    private final CouponCommand couponCommand;
    private final CouponIssueCommand couponIssueCommand;
    private final CouponIssueChecker couponIssueExistChecker;
    private final AppliedUserRepository appliedUserRepository;

    public CouponIssueService(MemberQuery memberQuery, CouponQuery couponQuery, CouponCommand couponCommand, CouponIssueCommand couponIssueCommand,
                              CouponIssueChecker couponIssueExistChecker, AppliedUserRepository appliedUserRepository) {
        this.memberQuery = memberQuery;
        this.couponQuery = couponQuery;
        this.couponCommand = couponCommand;
        this.couponIssueCommand = couponIssueCommand;
        this.couponIssueExistChecker = couponIssueExistChecker;
        this.appliedUserRepository = appliedUserRepository;
    }

    public UUID couponIssue(final Long memberId, final UUID couponToken) {
        Member member = getMember(memberId);

        couponIssueExistChecker.duplicateCouponIssueCheck(memberId, couponToken);

        Coupon coupon = couponQuery.getCouponInfoLock(couponToken);
        CouponIssue couponPublish = saveCouponIssue( member, coupon);

        coupon.useCoupon();
        couponCommand.modify(coupon);

        return couponPublish.getCouponIssueToken();
    }

    @LimitedCountLock(key = "key", waitTime = 20L)
    public UUID couponIssueRedissonAop(final Long memberId, final UUID key) {
        Member member = getMember(memberId);

        appliedUserRepository.addRedisSet(key.toString(), memberId.toString());

        Coupon coupon = couponQuery.getCoupon(key);
        CouponIssue couponIssue = saveCouponIssue(member, coupon);

        coupon.useCoupon();
        couponCommand.modify(coupon);

        return couponIssue.getCouponIssueToken();
    }

    private CouponIssue saveCouponIssue(Member member, Coupon coupon) {
        CouponIssue couponIssue = new CouponIssue(UUID.randomUUID(), member, coupon, false);
        return couponIssueCommand.save(couponIssue);
    }

    private Member getMember(Long memberId) {
        return memberQuery.findById(memberId);
    }
}
