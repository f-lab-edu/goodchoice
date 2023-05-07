package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.repositories.CouponRepository;
import com.flab.goodchoice.coupon.domain.repositories.InMemoryCouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CouponServiceTest {

    private CouponService couponService;
    private CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        couponRepository = new InMemoryCouponRepository();

        couponService = new CouponService(couponRepository);
    }

    @DisplayName("쿠폰 생성")
    @Test
    void createCoupon() {
        final String couponName = "10%할인";
        final int stock = 100;

        couponService.create(couponName, stock);

        Coupon coupon = couponRepository.findById(1L).get();

        assertAll(
                () -> assertThat(coupon.getCouponName()).isEqualTo(couponName),
                () -> assertThat(coupon.getStock()).isEqualTo(stock)
        );
    }

    @DisplayName("쿠폰명 미입력시 에러")
    @Test
    void notInputCouponName() {
        final String couponName = "";
        final int stock = 100;

        assertThatThrownBy(() -> couponService.create(couponName, stock))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 갯수가 음수이면 에러")
    @Test
    void negativeStock() {
        final String couponName = "10%할인";
        final int stock = -1;

        assertThatThrownBy(() -> couponService.create(couponName, stock))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
