package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponType;
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

class CouponQueryServiceTest {

    private CouponQueryService couponQueryService;
    private CouponRepository couponRepository;

    final UUID couponToken = UUID.randomUUID();
    final String couponName = "10%할인";
    final int stock = 100;

    @BeforeEach
    void setUp() {
        couponRepository = new InMemoryCouponRepository();

        couponQueryService = new CouponQueryService(couponRepository);

        final Coupon coupon = new Coupon(couponToken, couponName, stock, CouponType.DISCOUNT, 10, State.ACTIVITY);
        couponRepository.save(coupon);
    }

    @DisplayName("쿠폰 전체 조회")
    @Test
    void getAllCoupons() {
        final String couponNameSecond = "10%할인";
        final int stockSecond = 100;

        final Coupon couponSecond = new Coupon(UUID.randomUUID(), couponNameSecond, stockSecond, CouponType.DISCOUNT, 10, State.ACTIVITY);
        couponRepository.save(couponSecond);

        final List<CouponInfoResponse> result = couponQueryService.getAllCoupons();

        assertAll(
                () -> assertThat(result).hasSize(2),
                () -> assertThat(result.get(0).couponName()).isEqualTo(couponName),
                () -> assertThat(result.get(0).stock()).isEqualTo(stock),
                () -> assertThat(result.get(1).couponName()).isEqualTo(couponNameSecond),
                () -> assertThat(result.get(1).stock()).isEqualTo(stockSecond)
        );
    }

    @DisplayName("쿠폰 단건 조회")
    @Test
    void getCouponDetail() {
        final CouponInfoResponse result = couponQueryService.getCouponDetail(couponToken);

        assertAll(
                () -> assertThat(result.couponName()).isEqualTo(couponName),
                () -> assertThat(result.stock()).isEqualTo(stock)
        );
    }

    @DisplayName("해당 쿠폰이 없다면 에러")
    @Test
    void notFoundCoupon() {
        assertThatThrownBy(() -> couponQueryService.getCouponDetail(UUID.randomUUID()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
