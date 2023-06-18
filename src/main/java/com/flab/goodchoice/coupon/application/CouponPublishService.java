package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.common.aop.RedissonLock;
import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponPublish;
import com.flab.goodchoice.coupon.domain.Member;
import com.flab.goodchoice.coupon.dto.MemberSpecificCouponResponse;
import com.flab.goodchoice.coupon.exception.CouponError;
import com.flab.goodchoice.coupon.exception.CouponException;
import com.flab.goodchoice.coupon.infrastructure.repositories.AppliedUserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class CouponPublishService {

    private final MemberQuery memberQuery;
    private final CouponQuery couponQuery;
    private final CouponCommand couponCommand;
    private final CouponPublishQuery couponPublishQuery;
    private final CouponPublishCommand couponPublishCommand;
    private final AppliedUserRepository appliedUserRepository;

    public CouponPublishService(MemberQuery memberQuery, CouponQuery couponQuery, CouponCommand couponCommand, CouponPublishQuery couponPublishQuery, CouponPublishCommand couponPublishCommand, AppliedUserRepository appliedUserRepository) {
        this.memberQuery = memberQuery;
        this.couponQuery = couponQuery;
        this.couponCommand = couponCommand;
        this.couponPublishQuery = couponPublishQuery;
        this.couponPublishCommand = couponPublishCommand;
        this.appliedUserRepository = appliedUserRepository;
    }

    public UUID createCouponPublish(final Long memberId, final UUID couponToken) {
        Member member = getMemberById(memberId);

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

    @RedissonLock(key = "key", waitTime = 20L)
    public UUID createCouponPublishRedissonAop(final Long memberId, final UUID key) {
        Member member = getMemberById(memberId);

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

    private Member getMemberById(Long memberId) {
        return memberQuery.findById(memberId);
    }

    @Transactional(readOnly = true)
    public List<MemberSpecificCouponResponse> getMemberCoupon(Long memberId) {
        List<CouponPublish> couponPublishes = couponPublishQuery.findCouponHistoryFetchByMemberId(memberId);
        return couponPublishes.stream()
                .map(couponPublish -> MemberSpecificCouponResponse.of(couponPublish.getCoupon()))
                .toList();
    }
}
