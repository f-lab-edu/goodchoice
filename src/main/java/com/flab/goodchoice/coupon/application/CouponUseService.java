package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.*;
import com.flab.goodchoice.coupon.dto.CouponUsedCancelInfoResponse;
import com.flab.goodchoice.coupon.dto.CouponUsedInfoResponse;
import com.flab.goodchoice.member.application.MemberQuery;
import com.flab.goodchoice.member.domain.model.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
public class CouponUseService {

    private final MemberQuery memberQuery;
    private final CouponPublishQuery couponPublishQuery;
    private final CouponPublishCommand couponPublishCommand;
    private final CouponUseHistoryQuery couponUseHistoryQuery;
    private final CouponUseHistoryCommand couponUseHistoryCommand;

    public CouponUseService(MemberQuery memberQuery, CouponPublishQuery couponPublishQuery, CouponPublishCommand couponPublishCommand,
                            CouponUseHistoryQuery couponUseHistoryQuery, CouponUseHistoryCommand couponUseHistoryCommand) {
        this.memberQuery = memberQuery;
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

        couponPublish.use();
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

    private Member getMemberById(Long memberId) {
        return memberQuery.findById(memberId);
    }
}
