package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.common.aop.RedissonLock;
import com.flab.goodchoice.coupon.domain.*;
import com.flab.goodchoice.coupon.dto.CouponUsedCancelInfoResponse;
import com.flab.goodchoice.coupon.dto.CouponUsedInfoResponse;
import com.flab.goodchoice.coupon.dto.MemberSpecificCouponResponse;
import com.flab.goodchoice.coupon.exception.CouponError;
import com.flab.goodchoice.coupon.exception.CouponException;
import com.flab.goodchoice.member.application.MemberQuery;
import com.flab.goodchoice.member.domain.model.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional
@Service
public class CouponUseService {

    private final MemberQuery memberQuery;
    private final CouponQuery couponQuery;
    private final CouponCommand couponCommand;
    private final CouponPublishQuery couponPublishQuery;
    private final CouponPublishCommand couponPublishCommand;
    private final CouponUseHistoryQuery couponUseHistoryQuery;
    private final CouponUseHistoryCommand couponUseHistoryCommand;

    public CouponUseService(MemberQuery memberQuery, CouponQuery couponQuery, CouponCommand couponCommand, CouponPublishQuery couponPublishQuery, CouponPublishCommand couponPublishCommand,
                            CouponUseHistoryQuery couponUseHistoryQuery, CouponUseHistoryCommand couponUseHistoryCommand) {
        this.memberQuery = memberQuery;
        this.couponQuery = couponQuery;
        this.couponCommand = couponCommand;
        this.couponPublishQuery = couponPublishQuery;
        this.couponPublishCommand = couponPublishCommand;
        this.couponUseHistoryQuery = couponUseHistoryQuery;
        this.couponUseHistoryCommand = couponUseHistoryCommand;
    }

    public CouponUsedInfoResponse useCoupon(final Long memberId, final UUID couponPublishToken, final int price) {
        Member member = getMemberById(memberId);
        CouponPublish couponPublish = couponPublishQuery.findByCouponPublishTokenAndMemberEntityId(couponPublishToken, memberId);

        Coupon coupon = couponPublish.getCoupon();

        CouponCalculator couponCalculator = coupon.getCouponType().couponCalculator(price, coupon.getDiscountValue());
        int discountPrice = couponCalculator.discountPriceCalculation();
        int resultPrice = couponCalculator.useCalculation();

        couponUseHistoryCommand.save(new CouponUseHistory(member, coupon, price, discountPrice, UseState.USE));

        couponPublish.used();
        couponPublishCommand.modify(couponPublish);

        return new CouponUsedInfoResponse(couponPublishToken, price, discountPrice, resultPrice);
    }

    public CouponUsedCancelInfoResponse usedCouponCancel(final Long memberId, final UUID couponPublishToken, final int price) {
        Member member = getMemberById(memberId);
        CouponPublish couponPublish = couponPublishQuery.findByCouponPublishTokenAndMemberEntityId(couponPublishToken, memberId);
        Coupon coupon = couponPublish.getCoupon();

        CouponUseHistory couponUseHistory = couponUseHistoryQuery.findByMemberIdAndCouponEntityId(member, coupon);
        couponUseHistory.cancel();
        couponUseHistoryCommand.modify(couponUseHistory);

        couponPublish.cancel();
        couponPublishCommand.modify(couponPublish);

        CouponCalculator couponCalculator = coupon.getCouponType().couponCalculator(price, coupon.getDiscountValue());

        return new CouponUsedCancelInfoResponse(couponPublishToken, price, couponCalculator.usedCancelCalculation());
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

    @RedissonLock(key = "key", target = "memberId", waitTime = 20L)
    public UUID createCouponPublishRedissonAop(UUID key, Long memberId) {
        Member member = getMemberById(memberId);

        couponIssueValidation(key, memberId);

        Coupon coupon = couponQuery.findByCouponToken(key);
        CouponPublish couponPublish = saveCouponPublish( member, coupon);

        coupon.useCoupon();
        couponCommand.modify(coupon);

        return couponPublish.getCouponPublishToken();
    }

    private CouponPublish saveCouponPublish(Member member, Coupon coupon) {
        CouponPublish couponPublish = new CouponPublish(UUID.randomUUID(), member, coupon, false);
        return couponPublishCommand.save(couponPublish);
    }

    private void couponIssueValidation(UUID key, Long memberId) {
        boolean apply = couponPublishQuery.existsByMemberEntityIdAndCouponPublishToken(memberId, key);

        if (apply) {
            throw new CouponException(CouponError.NOT_DUPLICATION_COUPON);
        }
    }

    @Transactional(readOnly = true)
    public List<MemberSpecificCouponResponse> getMemberCoupon(Long memberId) {
        List<CouponPublish> couponPublishes = couponPublishQuery.findCouponHistoryFetchByMemberId(memberId);
        return couponPublishes.stream()
                .map(couponPublish -> MemberSpecificCouponResponse.of(couponPublish.getCoupon()))
                .toList();
    }

    private Member getMemberById(Long memberId) {
        return memberQuery.findById(memberId);
    }
}
