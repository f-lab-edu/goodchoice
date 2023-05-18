package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponPublishHistory;
import com.flab.goodchoice.coupon.domain.CouponType;
import com.flab.goodchoice.coupon.domain.repositories.CouponHistoryPublishRepository;
import com.flab.goodchoice.coupon.domain.repositories.CouponRepository;
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

    private final CouponRepository couponRepository;
    private final CouponHistoryPublishRepository couponHistoryPublishRepository;

    public CouponUseService(CouponRepository couponRepository, CouponHistoryPublishRepository couponHistoryPublishRepository) {
        this.couponRepository = couponRepository;
        this.couponHistoryPublishRepository = couponHistoryPublishRepository;
    }

    public CouponUsedInfoResponse couponUsed(final UUID couponToken, final int price) {
        Coupon coupon = couponRepository.findByCouponToken(couponToken).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        CouponType couponType = coupon.getCouponType();

        int discountPrice = couponType.discountPriceCalculation(price, coupon.getDiscountValue());
        int resultPrice = couponType.usedCalculation(price, coupon.getDiscountValue());
        return new CouponUsedInfoResponse(couponToken, price, discountPrice, resultPrice);
    }

    public CouponUsedCancelInfoResponse couponUsedCancel(final UUID couponToken, final int price) {
        Coupon coupon = couponRepository.findByCouponToken(couponToken).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        CouponType couponType = coupon.getCouponType();

        int usedCancelPrice = couponType.usedCancelCalculation(price, coupon.getDiscountValue());
        return new CouponUsedCancelInfoResponse(couponToken, price, usedCancelPrice);
    }

    public UUID couponPublish(final Long memberId, final UUID couponToken) {
        Coupon coupon = couponRepository.findByCouponToken(couponToken).orElseThrow();
        CouponPublishHistory couponPublishHistory = new CouponPublishHistory(UUID.randomUUID(), memberId, coupon);
        couponHistoryPublishRepository.save(couponPublishHistory);

        coupon.usedCoupon();

        return couponPublishHistory.getCouponPublishToken();
    }

    public List<MemberSpecificCouponResponse> getMemberCoupon(Long memberId) {
        List<CouponPublishHistory> couponPublishHistories = couponHistoryPublishRepository.findCouponHistoryFetchByMemberId(memberId);

        return couponPublishHistories.stream()
                .map(couponPublishHistory -> new MemberSpecificCouponResponse(couponPublishHistory.getCoupon().getCouponToken(), couponPublishHistory.getCoupon().getCouponName(), couponPublishHistory.getCoupon().getCouponType(), couponPublishHistory.getCoupon().getDiscountValue()))
                .toList();
    }
}
