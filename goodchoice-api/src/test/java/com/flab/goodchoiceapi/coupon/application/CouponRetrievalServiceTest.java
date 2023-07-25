package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.domain.CouponType;
import com.flab.goodchoicecoupon.domain.State;
import com.flab.goodchoiceapi.coupon.dto.CouponRetrievalResponse;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponRepository;
import com.flab.goodchoicecoupon.infrastructure.repositories.InMemoryCouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CouponRetrievalServiceTest {

    private CouponRetrievalService couponRetrievalService;
    private CouponRepository couponRepository;

    final UUID couponToken = UUID.randomUUID();
    final String couponName = "10%할인";
    final int stock = 100;

    @BeforeEach
    void setUp() {
        couponRepository = new InMemoryCouponRepository();

        couponRetrievalService = new FakeCouponRetrievalService(couponRepository).createCouponRetrievalService();

        final CouponEntity coupon = new CouponEntity(couponToken, couponName, stock, CouponType.DISCOUNT, 10, State.ACTIVITY);
        couponRepository.save(coupon);
    }


    @DisplayName("쿠폰 전체 조회")
    @Test
    void getAllCoupons() {
        final String couponNameSecond = "10%할인";
        final int stockSecond = 100;

        final CouponEntity couponSecond = new CouponEntity(UUID.randomUUID(), couponNameSecond, stockSecond, CouponType.DISCOUNT, 10, State.ACTIVITY);
        couponRepository.save(couponSecond);

        final List<CouponRetrievalResponse> result = couponRetrievalService.getAllCoupons();

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
    void getCouponInfo() {
        final CouponRetrievalResponse result = couponRetrievalService.getCoupon(couponToken);

        assertAll(
                () -> assertThat(result.couponName()).isEqualTo(couponName),
                () -> assertThat(result.stock()).isEqualTo(stock)
        );
    }

    @DisplayName("해당 쿠폰이 없다면 에러")
    @Test
    void notFoundCoupon() {
        assertThatThrownBy(() -> couponRetrievalService.getCoupon(UUID.randomUUID()))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
