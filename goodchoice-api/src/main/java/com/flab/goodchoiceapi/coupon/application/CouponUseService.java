package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoiceapi.coupon.dto.CouponUsedCancelInfoResponse;
import com.flab.goodchoiceapi.coupon.dto.CouponUsedInfoResponse;
import com.flab.goodchoiceapi.member.application.MemberQuery;
import com.flab.goodchoicecoupon.application.CouponIssueCommand;
import com.flab.goodchoicecoupon.application.CouponIssueQuery;
import com.flab.goodchoicecoupon.application.CouponUseHistoryCommand;
import com.flab.goodchoicecoupon.application.CouponUseHistoryQuery;
import com.flab.goodchoicecoupon.domain.*;
import com.flab.goodchoicemember.domain.model.Member;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
@Service
public class CouponUseService {

    private final MemberQuery memberQuery;
    private final CouponIssueQuery couponIssueQuery;
    private final CouponIssueCommand couponIssueCommand;
    private final CouponUseHistoryQuery couponUseHistoryQuery;
    private final CouponUseHistoryCommand couponUseHistoryCommand;

    public CouponUseService(MemberQuery memberQuery, CouponIssueQuery couponIssueQuery, CouponIssueCommand couponIssueCommand,
                            CouponUseHistoryQuery couponUseHistoryQuery, CouponUseHistoryCommand couponUseHistoryCommand) {
        this.memberQuery = memberQuery;
        this.couponIssueQuery = couponIssueQuery;
        this.couponIssueCommand = couponIssueCommand;
        this.couponUseHistoryQuery = couponUseHistoryQuery;
        this.couponUseHistoryCommand = couponUseHistoryCommand;
    }

    public CouponUsedInfoResponse useCoupon(final Long memberId, final UUID couponIssueToken, final int price) {
        CouponIssue couponPublish = couponIssueQuery.getCouponIssue(couponIssueToken, memberId);

        Coupon coupon = couponPublish.getCoupon();

        CouponCalculator couponCalculator = coupon.getCouponType().couponCalculator(price, coupon.getDiscountValue());
        int discountPrice = couponCalculator.discountPriceCalculation();
        int resultPrice = couponCalculator.useCalculation();

        couponUseHistoryCommand.save(new CouponUseHistory(memberId, coupon, price, discountPrice, UseState.USE));

        couponPublish.use();
        couponIssueCommand.modify(couponPublish);

        return new CouponUsedInfoResponse(couponIssueToken, price, discountPrice, resultPrice);
    }

    public CouponUsedCancelInfoResponse usedCouponCancel(final Long memberId, final UUID couponIssueToken, final int price) {
        Member member = getMemberById(memberId);
        CouponIssue couponPublish = couponIssueQuery.getCouponIssue(couponIssueToken, memberId);
        Coupon coupon = couponPublish.getCoupon();

        CouponUseHistory couponUseHistory = couponUseHistoryQuery.getCouponUseHistory(memberId, coupon);
        couponUseHistory.cancel();
        couponUseHistoryCommand.modify(couponUseHistory);

        couponPublish.cancel();
        couponIssueCommand.modify(couponPublish);

        CouponCalculator couponCalculator = coupon.getCouponType().couponCalculator(price, coupon.getDiscountValue());

        return new CouponUsedCancelInfoResponse(couponIssueToken, price, couponCalculator.usedCancelCalculation());
    }

    private Member getMemberById(Long memberId) {
        return memberQuery.findById(memberId);
    }
}
