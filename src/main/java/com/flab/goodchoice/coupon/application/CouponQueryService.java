package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.repositories.CouponRepository;
import com.flab.goodchoice.coupon.dto.CouponInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class CouponQueryService {

    private final CouponRepository couponRepository;

    public CouponQueryService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    public List<CouponInfoResponse> getAllCoupons() {
        return couponRepository.findAll().stream()
                .map(coupon -> new CouponInfoResponse(coupon.getCouponToken(), coupon.getCouponName(), coupon.getStock(), coupon.getState()))
                .toList();
    }

    public CouponInfoResponse getCouponDetail(final UUID couponToken) {
        Coupon coupon = couponRepository.findByCouponToken(couponToken).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        return new CouponInfoResponse(coupon.getCouponToken(), coupon.getCouponName(), coupon.getStock(), coupon.getState());
    }
}
