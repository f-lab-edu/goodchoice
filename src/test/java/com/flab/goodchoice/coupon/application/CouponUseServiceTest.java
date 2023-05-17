package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponType;
import com.flab.goodchoice.coupon.domain.State;
import com.flab.goodchoice.coupon.domain.repositories.CouponHistoryPublishRepository;
import com.flab.goodchoice.coupon.domain.repositories.CouponRepository;
import com.flab.goodchoice.coupon.domain.repositories.InMemoryCouponHistoryPublishRepository;
import com.flab.goodchoice.coupon.domain.repositories.InMemoryCouponRepository;
import com.flab.goodchoice.coupon.dto.CouponUsedCancelInfoResponse;
import com.flab.goodchoice.coupon.dto.CouponUsedInfoResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CouponUseServiceTest {

    private CouponUseService couponUseService;
    private CouponRepository couponRepository;
    private CouponHistoryPublishRepository couponHistoryPublishRepository;

    final UUID couponTokenDiscount = UUID.randomUUID();
    final String couponNameDiscount = "10%할인";
    final int stockDiscount = 100;

    final UUID couponTokenDeduction = UUID.randomUUID();
    final String couponNameDeduction = "10000원 차감";
    final int stockDeduction = 100;

    Coupon couponDiscount;
    Coupon couponDeduction;

    @BeforeEach
    void setUp() {
        couponRepository = new InMemoryCouponRepository();
        couponHistoryPublishRepository = new InMemoryCouponHistoryPublishRepository();
        couponUseService = new CouponUseService(couponRepository, couponHistoryPublishRepository);

        couponDiscount = new Coupon(couponTokenDiscount, couponNameDiscount, stockDiscount, CouponType.DISCOUNT, 10, State.ACTIVITY);
        couponRepository.save(couponDiscount);

        couponDeduction = new Coupon(couponTokenDeduction, couponNameDeduction, stockDeduction, CouponType.DEDUCTION, 10000, State.ACTIVITY);
        couponRepository.save(couponDeduction);
    }

    @DisplayName("쿠폰 사용-금액 할인")
    @ParameterizedTest
    @CsvSource(value = {"10000:1000:9000", "15000:1500:13500", "20000:2000:18000"}, delimiter = ':')
    void couponUsedDiscount(int prePrice, int discountPrice, int result) {
        CouponUsedInfoResponse couponUsedInfoResponse = couponUseService.couponUsed(couponTokenDiscount, prePrice);

        assertAll(
                () -> assertThat(couponUsedInfoResponse.discountPrice()).isEqualTo(discountPrice),
                () -> assertThat(couponUsedInfoResponse.resultPrice()).isEqualTo(result)
        );
    }

    @DisplayName("쿠폰 사용 취소-금액 할인")
    @ParameterizedTest
    @CsvSource(value = {"9000:10000", "13500:15000", "18000:20000"}, delimiter = ':')
    void couponUsedCancelDiscount(int prePrice, int result) {
        CouponUsedCancelInfoResponse couponUsedCancelInfoResponse = couponUseService.couponUsedCancel(couponTokenDiscount, prePrice);

        assertThat(couponUsedCancelInfoResponse.resultPrice()).isEqualTo(result);
    }

    @DisplayName("쿠폰 사용-금액 차감")
    @ParameterizedTest
    @CsvSource(value = {"10000:10000:0", "15000:10000:5000", "20000:10000:10000"}, delimiter = ':')
    void couponUsedDeduction(int prePrice, int discountPrice, int result) {
        CouponUsedInfoResponse couponUsedInfoResponse = couponUseService.couponUsed(couponTokenDeduction, prePrice);

        assertAll(
                () -> assertThat(couponUsedInfoResponse.discountPrice()).isEqualTo(discountPrice),
                () -> assertThat(couponUsedInfoResponse.resultPrice()).isEqualTo(result)
        );
    }

    @DisplayName("쿠폰 사용 취소-금액 차감")
    @ParameterizedTest
    @CsvSource(value = {"0:10000", "5000:15000", "10000:20000"}, delimiter = ':')
    void couponUsedCancelDeduction(int prePrice, int result) {
        CouponUsedCancelInfoResponse couponUsedCancelInfoResponse = couponUseService.couponUsedCancel(couponTokenDeduction, prePrice);

        assertThat(couponUsedCancelInfoResponse.resultPrice()).isEqualTo(result);
    }

    @DisplayName("회원별 쿠폰 등록")
    @Test
    void couponPublish() {
        couponUseService.couponPublish(1L, couponTokenDiscount);

        assertThat(couponDiscount.getStock()).isEqualTo(99);
        assertThat(couponHistoryPublishRepository.countByCoupon(couponDiscount)).isEqualTo(1);
    }

    @DisplayName("모두 소진된 쿠폰을 회원이 등록하면 에러")
    @Test
    void couponCountZeroPublish() {
        couponDiscount.modify(couponNameDiscount, 0);

        assertThatThrownBy(() -> couponUseService.couponPublish(1L, couponTokenDiscount))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
