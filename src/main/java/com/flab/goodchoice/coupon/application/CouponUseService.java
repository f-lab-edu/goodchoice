package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponPublish;
import com.flab.goodchoice.coupon.domain.CouponType;
import com.flab.goodchoice.coupon.domain.Member;
import com.flab.goodchoice.coupon.domain.repositories.CouponPublishRepository;
import com.flab.goodchoice.coupon.domain.repositories.CouponRepository;
import com.flab.goodchoice.coupon.domain.repositories.MemberRepository;
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

    private final MemberRepository memberRepository;
    private final CouponRepository couponRepository;
    private final CouponPublishRepository couponPublishRepository;

    public CouponUseService(MemberRepository memberRepository, CouponRepository couponRepository, CouponPublishRepository couponPublishRepository) {
        this.memberRepository = memberRepository;
        this.couponRepository = couponRepository;
        this.couponPublishRepository = couponPublishRepository;
    }

    public CouponUsedInfoResponse couponUsed(final Long memberId, final UUID couponToken, final int price) {
        getMemberById(memberId);

        Coupon coupon = couponRepository.findByCouponToken(couponToken).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));

        CouponPublish couponPublish = couponPublishRepository.findByCouponAndMemberId(coupon, memberId).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 보유하고 있지 않습니다."));
        couponPublish.used();

        CouponType couponType = coupon.getCouponType();

        int discountPrice = couponType.discountPriceCalculation(price, coupon.getDiscountValue());
        int resultPrice = couponType.usedCalculation(price, coupon.getDiscountValue());
        return new CouponUsedInfoResponse(couponToken, price, discountPrice, resultPrice);
    }

    public CouponUsedCancelInfoResponse couponUsedCancel(final Long memberId, final UUID couponToken, final int price) {
        getMemberById(memberId);

        Coupon coupon = couponRepository.findByCouponToken(couponToken).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));

        CouponPublish couponPublish = couponPublishRepository.findByCouponAndMemberId(coupon, memberId).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 보유하고 있지 않습니다."));

        couponPublish.cancel();

        CouponType couponType = coupon.getCouponType();

        int usedCancelPrice = couponType.usedCancelCalculation(price, coupon.getDiscountValue());
        return new CouponUsedCancelInfoResponse(couponToken, price, usedCancelPrice);
    }

    public UUID couponPublish(final Long memberId, final UUID couponToken) {
        getMemberById(memberId);

        Coupon coupon = couponRepository.findByCouponToken(couponToken).orElseThrow();
        CouponPublish couponPublish = new CouponPublish(UUID.randomUUID(), memberId, coupon, false);
        couponPublishRepository.save(couponPublish);

        coupon.usedCoupon();

        return couponPublish.getCouponPublishToken();
    }

    @Transactional(readOnly = true)
    public List<MemberSpecificCouponResponse> getMemberCoupon(Long memberId) {
        getMemberById(memberId);

        List<CouponPublish> couponPublishes = couponPublishRepository.findCouponHistoryFetchByMemberId(memberId);

        return couponPublishes.stream()
                .map(couponPublishHistory -> new MemberSpecificCouponResponse(couponPublishHistory.getCoupon().getCouponToken(), couponPublishHistory.getCoupon().getCouponName(), couponPublishHistory.getCoupon().getCouponType(), couponPublishHistory.getCoupon().getDiscountValue()))
                .toList();
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new IllegalArgumentException("해당 회원을 찾을 수 없습니다."));
    }
}
