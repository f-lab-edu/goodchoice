package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.common.aop.LimitedCountLock;
import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponPublish;
import com.flab.goodchoice.coupon.exception.CouponError;
import com.flab.goodchoice.coupon.exception.CouponException;
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
    private final CouponPublishQuery couponPublishQuery;
    private final CouponPublishCommand couponPublishCommand;
    private final AppliedUserRepository appliedUserRepository;

    public CouponIssueService(MemberQuery memberQuery, CouponQuery couponQuery, CouponCommand couponCommand, CouponPublishQuery couponPublishQuery, CouponPublishCommand couponPublishCommand, AppliedUserRepository appliedUserRepository) {
        this.memberQuery = memberQuery;
        this.couponQuery = couponQuery;
        this.couponCommand = couponCommand;
        this.couponPublishQuery = couponPublishQuery;
        this.couponPublishCommand = couponPublishCommand;
        this.appliedUserRepository = appliedUserRepository;
    }

    public UUID couponIssuance(final Long memberId, final UUID couponToken) {
        Member member = getMember(memberId);

        boolean existsCoupon = couponPublishQuery.existsByMemberEntityIdAndCouponPublishToken(memberId, couponToken);
        if (existsCoupon) {
            throw new CouponException(CouponError.NOT_DUPLICATION_COUPON);
        }

        Coupon coupon = couponQuery.findByCouponTokenLock(couponToken);

        CouponPublish couponPublish = saveCouponPublish( member, coupon);

        coupon.useCoupon();
        couponCommand.modify(coupon);

        return couponPublish.getCouponPublishToken();
    }

    @LimitedCountLock(key = "key", waitTime = 20L)
    public UUID couponIssuanceRedissonAop(final Long memberId, final UUID key) {
        Member member = getMember(memberId);

        Long apply = appliedUserRepository.addRedisSet(key, memberId);

        if (apply != 1) {
            throw new CouponException(CouponError.NOT_DUPLICATION_COUPON);
        }

        Coupon coupon = couponQuery.findByCouponToken(key);

        CouponPublish couponPublish = saveCouponPublish(member, coupon);

        coupon.useCoupon();
        couponCommand.modify(coupon);

        return couponPublish.getCouponPublishToken();
    }

    private CouponPublish saveCouponPublish(Member member, Coupon coupon) {
        CouponPublish couponPublish = new CouponPublish(UUID.randomUUID(), member, coupon, false);
        return couponPublishCommand.save(couponPublish);
    }

    private Member getMember(Long memberId) {
        return memberQuery.findById(memberId);
    }
}
