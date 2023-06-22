package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.*;
import com.flab.goodchoice.coupon.dto.CouponUsedCancelInfoResponse;
import com.flab.goodchoice.coupon.dto.CouponUsedInfoResponse;
import com.flab.goodchoice.coupon.dto.MemberSpecificCouponResponse;
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
        CouponType couponType = coupon.getCouponType();

        int discountPrice = couponType.discountPriceCalculation(price, coupon.getDiscountValue());
        int resultPrice = couponType.useCalculation(price, coupon.getDiscountValue());
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

        CouponType couponType = coupon.getCouponType();
        int usedCancelPrice = couponType.usedCancelCalculation(price, coupon.getDiscountValue());

        return new CouponUsedCancelInfoResponse(couponPublishToken, price, usedCancelPrice);
    }

    public UUID createCouponPublish(final Long memberId, final UUID couponToken) {
        Member member = getMemberById(memberId);
        Coupon coupon = couponQuery.findByCouponTokenLock(couponToken);

        CouponPublish couponPublish = new CouponPublish(UUID.randomUUID(), member, coupon, false);
        couponPublishCommand.save(couponPublish);

        coupon.useCoupon();
        couponCommand.modify(coupon);

        return couponPublish.getCouponPublishToken();
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
