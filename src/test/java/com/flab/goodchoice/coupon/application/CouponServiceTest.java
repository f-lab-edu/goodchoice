package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.State;
import com.flab.goodchoice.coupon.domain.repositories.CouponRepository;
import com.flab.goodchoice.coupon.domain.repositories.InMemoryCouponRepository;
import com.flab.goodchoice.coupon.dto.CouponInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

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

    @DisplayName("쿠폰 전체 조회")
    @Test
    void getAllCoupons() {
        final String couponNameFirst = "10%할인";
        final int stockFirst = 100;

        couponService.create(couponNameFirst, stockFirst);

        final String couponNameSecond = "10%할인";
        final int stockSecond = 100;

        couponService.create(couponNameSecond, stockSecond);

        final List<CouponInfoResponse> result = couponService.getAllCoupons();

        assertAll(
                () -> assertThat(result).hasSize(2),
                () -> assertThat(result.get(0).couponName()).isEqualTo(couponNameFirst),
                () -> assertThat(result.get(0).stock()).isEqualTo(stockFirst),
                () -> assertThat(result.get(1).couponName()).isEqualTo(couponNameSecond),
                () -> assertThat(result.get(1).stock()).isEqualTo(stockSecond)
        );
    }

    @DisplayName("쿠폰 단건 조회")
    @Test
    void getCouponDetail() {
        final Coupon coupon = new Coupon(UUID.randomUUID(), "10%할인", 100, State.ACTIVITY);
        couponRepository.save(coupon);

        final CouponInfoResponse result = couponService.getCouponDetail(coupon.getCouponToken());

        assertAll(
                () -> assertThat(result.couponName()).isEqualTo(coupon.getCouponName()),
                () -> assertThat(result.stock()).isEqualTo(coupon.getStock())
        );
    }

    @DisplayName("해당 쿠폰이 없다면 에러")
    @Test
    void notFoundCoupon() {
        final Coupon coupon = new Coupon(UUID.randomUUID(), "10%할인", 100, State.ACTIVITY);
        couponRepository.save(coupon);

        assertThatThrownBy(() -> couponService.getCouponDetail(UUID.randomUUID()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 정보 수정")
    @Test
    void modifyCoupon() {
        final Coupon coupon = new Coupon(UUID.randomUUID(), "10%할인", 100, State.ACTIVITY);
        couponRepository.save(coupon);

        couponService.modifyCoupon(coupon.getCouponToken(), "15%할인", 200);

        assertAll(
                () -> assertThat(coupon.getCouponName()).isEqualTo("15%할인"),
                () -> assertThat(coupon.getStock()).isEqualTo(200)
        );
    }

    @DisplayName("쿠폰 정보 수정시 쿠폰명 미입력 에러")
    @Test
    void modifyNotInputCouponName() {
        final Coupon coupon = new Coupon(UUID.randomUUID(), "10%할인", 100, State.ACTIVITY);
        couponRepository.save(coupon);

        final String couponName = "";
        final int stock = 200;

        assertThatThrownBy(() -> couponService.modifyCoupon(coupon.getCouponToken(), couponName, stock))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 정보 수정시 쿠폰 갯수가 음수이면 에러")
    @Test
    void modifyNegativeStock() {
        final Coupon coupon = new Coupon(UUID.randomUUID(), "10%할인", 100, State.ACTIVITY);
        couponRepository.save(coupon);

        final String couponName = "15%할인";
        final int stock = -1;

        assertThatThrownBy(() -> couponService.modifyCoupon(coupon.getCouponToken(), couponName, stock))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 삭제")
    @Test
    void deleteCoupon() {
        final Coupon coupon = new Coupon(UUID.randomUUID(), "10%할인", 100, State.ACTIVITY);
        couponRepository.save(coupon);

        couponService.deleteCoupon(coupon.getCouponToken());

        assertThat(coupon.getState()).isEqualTo(State.INACTIVITY);
    }
}
