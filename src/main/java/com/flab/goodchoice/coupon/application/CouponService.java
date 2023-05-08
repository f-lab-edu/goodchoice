package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.State;
import com.flab.goodchoice.coupon.domain.repositories.CouponRepository;
import com.flab.goodchoice.coupon.dto.CouponInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional
    public void create(final String couponName, final int stock) {
        validation(couponName, stock);
        Coupon coupon = new Coupon(UUID.randomUUID(), couponName, stock, State.ACTIVITY);
        couponRepository.save(coupon);
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

    @Transactional
    public void modifyCoupon(final UUID couponToken, final String couponName, final int stock) {
        validation(couponName, stock);
        Coupon coupon = couponRepository.findByCouponToken(couponToken).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));

        coupon.modify(couponName, stock);
    }

    private void validation(final String couponName, final int stock) {
        if (!StringUtils.hasText(couponName)) {
            throw new IllegalArgumentException("쿠폰명을 입력해주세요.");
        }

        if (stock < 0) {
            throw new IllegalArgumentException("쿠폰 갯수는 음수가 될수 없습니다.");
        }
    }

    @Transactional
    public void deleteCoupon(UUID couponToken) {
        Coupon coupon = couponRepository.findByCouponToken(couponToken).orElseThrow(() -> new IllegalArgumentException("해당 쿠폰을 찾을 수 없습니다."));
        coupon.delete();
    }
}
