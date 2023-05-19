package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.*;
import com.flab.goodchoice.coupon.domain.repositories.*;
import com.flab.goodchoice.coupon.dto.CouponUsedCancelInfoResponse;
import com.flab.goodchoice.coupon.dto.CouponUsedInfoResponse;
import com.flab.goodchoice.coupon.dto.MemberSpecificCouponResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CouponUseServiceTest {

    private CouponUseService couponUseService;
    private MemberRepository memberRepository;
    private CouponRepository couponRepository;
    private CouponPublishRepository couponPublishRepository;

    final Long memberId = 1L;
    final Long noneMemberId = 2L;

    final Long couponIdDiscount = 1L;
    final UUID couponTokenDiscount = UUID.randomUUID();
    final String couponNameDiscount = "10%할인";
    final int stockDiscount = 100;
    final int discountValue = 10;

    final Long couponIdDeduction = 2L;
    final UUID couponTokenDeduction = UUID.randomUUID();
    final String couponNameDeduction = "10000원 차감";
    final int stockDeduction = 100;
    final int deductionValue = 10000;

    final UUID CouponPublishToken = UUID.randomUUID();

    Coupon couponDiscount;
    Coupon couponDeduction;

    @BeforeEach
    void setUp() {
        memberRepository = new InMemoryMemberRepository();
        couponRepository = new InMemoryCouponRepository();
        couponPublishRepository = new InMemoryCouponPublishRepository();
        couponUseService = new CouponUseService(memberRepository, couponRepository, couponPublishRepository);

        couponDiscount = new Coupon(couponIdDiscount, couponTokenDiscount, couponNameDiscount, stockDiscount, CouponType.DISCOUNT, discountValue, State.ACTIVITY);
        couponRepository.save(couponDiscount);

        couponDeduction = new Coupon(couponIdDeduction, couponTokenDeduction, couponNameDeduction, stockDeduction, CouponType.DEDUCTION, deductionValue, State.ACTIVITY);
        couponRepository.save(couponDeduction);
    }

    @DisplayName("쿠폰 사용-금액 할인")
    @ParameterizedTest
    @CsvSource(value = {"10000:1000:9000", "15000:1500:13500", "20000:2000:18000"}, delimiter = ':')
    void couponUsedDiscount(int prePrice, int discountPrice, int result) {
        memberRepository.save(new Member(memberId));
        couponPublishRepository.save(new CouponPublish(1L, CouponPublishToken, memberId, couponDiscount, false));

        CouponUsedInfoResponse couponUsedInfoResponse = couponUseService.couponUsed(memberId, couponTokenDiscount, prePrice);

        assertAll(
                () -> assertThat(couponUsedInfoResponse.discountPrice()).isEqualTo(discountPrice),
                () -> assertThat(couponUsedInfoResponse.resultPrice()).isEqualTo(result)
        );
    }

    @DisplayName("쿠폰 사용-금액 차감")
    @ParameterizedTest
    @CsvSource(value = {"10000:10000:0", "15000:10000:5000", "20000:10000:10000"}, delimiter = ':')
    void couponUsedDeduction(int prePrice, int discountPrice, int result) {
        memberRepository.save(new Member(memberId));
        couponPublishRepository.save(new CouponPublish(1L, CouponPublishToken, memberId, couponDeduction, false));

        CouponUsedInfoResponse couponUsedInfoResponse = couponUseService.couponUsed(memberId, couponTokenDeduction, prePrice);

        assertAll(
                () -> assertThat(couponUsedInfoResponse.discountPrice()).isEqualTo(discountPrice),
                () -> assertThat(couponUsedInfoResponse.resultPrice()).isEqualTo(result)
        );
    }

    @DisplayName("존재하지 않은 회원이 쿠폰 사용시 에러")
    @Test
    void noneMemberCouponUsedDeduction() {
        memberRepository.save(new Member(memberId));

        assertThatThrownBy(() -> couponUseService.couponUsed(noneMemberId, couponTokenDeduction, 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("존재하지 않은 쿠폰 사용시 에러")
    @Test
    void noneCouponUsedDeduction() {
        memberRepository.save(new Member(memberId));

        assertThatThrownBy(() -> couponUseService.couponUsed(memberId, UUID.randomUUID(), 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가지고 있지 않은 쿠폰 사용시 에러")
    @Test
    void memberNotCouponUsedDeduction() {
        memberRepository.save(new Member(memberId));
        couponPublishRepository.save(new CouponPublish(1L, CouponPublishToken, memberId, couponDiscount, false));

        assertThatThrownBy(() -> couponUseService.couponUsed(noneMemberId, couponTokenDeduction, 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 사용 취소-금액 할인")
    @ParameterizedTest
    @CsvSource(value = {"9000:10000", "13500:15000", "18000:20000"}, delimiter = ':')
    void couponUsedCancelDiscount(int prePrice, int result) {
        memberRepository.save(new Member(memberId));
        couponPublishRepository.save(new CouponPublish(1L, CouponPublishToken, memberId, couponDiscount, false));

        couponUseService.couponUsed(memberId, couponTokenDiscount, prePrice);

        CouponUsedCancelInfoResponse couponUsedCancelInfoResponse = couponUseService.couponUsedCancel(memberId, couponTokenDiscount, prePrice);

        assertThat(couponUsedCancelInfoResponse.resultPrice()).isEqualTo(result);
    }

    @DisplayName("쿠폰 사용 취소-금액 차감")
    @ParameterizedTest
    @CsvSource(value = {"0:10000", "5000:15000", "10000:20000"}, delimiter = ':')
    void couponUsedCancelDeduction(int prePrice, int result) {
        memberRepository.save(new Member(memberId));
        couponPublishRepository.save(new CouponPublish(1L, CouponPublishToken, memberId, couponDeduction, false));

        couponUseService.couponUsed(memberId, couponTokenDeduction, prePrice);

        CouponUsedCancelInfoResponse couponUsedCancelInfoResponse = couponUseService.couponUsedCancel(memberId, couponTokenDeduction, prePrice);

        assertThat(couponUsedCancelInfoResponse.resultPrice()).isEqualTo(result);
    }

    @DisplayName("존재하지 않은 회원이 쿠폰 취소시 에러")
    @Test
    void noneMemberCouponUsedCancelDiscount() {
        assertThatThrownBy(() -> couponUseService.couponUsedCancel(noneMemberId, couponTokenDiscount, 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("존재하지 않은 쿠폰 취소시 에러")
    @Test
    void noneCouponUsedCancelDiscount() {
        memberRepository.save(new Member(memberId));

        assertThatThrownBy(() -> couponUseService.couponUsedCancel(memberId, UUID.randomUUID(), 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가지고 있지 않은 쿠폰 취소시 에러")
    @Test
    void memberNotCouponUsedCancelDiscount() {
        memberRepository.save(new Member(memberId));
        couponPublishRepository.save(new CouponPublish(1L, CouponPublishToken, memberId, couponDiscount, false));

        assertThatThrownBy(() -> couponUseService.couponUsed(noneMemberId, couponTokenDeduction, 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("사용하지 않은 쿠폰 취소시 에러")
    @Test
    void notUsedCouponCancelDiscount() {
        memberRepository.save(new Member(memberId));
        couponPublishRepository.save(new CouponPublish(1L, UUID.randomUUID(), memberId, couponDiscount, false));

        assertThatThrownBy(() -> couponUseService.couponUsed(noneMemberId, couponTokenDiscount, 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원별 쿠폰 등록")
    @Test
    void couponPublish() {
        memberRepository.save(new Member(memberId));
        couponUseService.couponPublish(memberId, couponTokenDiscount);

        assertThat(couponDiscount.getStock()).isEqualTo(99);
        assertThat(couponPublishRepository.countByCoupon(couponDiscount)).isEqualTo(1);
    }

    @DisplayName("존재하지 않은 회원 쿠폰 등록시 에러")
    @Test
    void noneMemberCouponPublish() {
        memberRepository.save(new Member(memberId));

        assertThatThrownBy(() -> couponUseService.couponPublish(noneMemberId, couponTokenDiscount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("모두 소진된 쿠폰을 회원이 등록하면 에러")
    @Test
    void couponCountZeroPublish() {
        couponDiscount.modify(couponNameDiscount, 0);

        assertThatThrownBy(() -> couponUseService.couponPublish(memberId, couponTokenDiscount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원이 가진 쿠폰 목록 조회")
    @Test
    void memberGetCouponList() {
        memberRepository.save(new Member(memberId));
        couponUseService.couponPublish(memberId, couponTokenDiscount);

        List<MemberSpecificCouponResponse> memberSpecificCouponResponses = couponUseService.getMemberCoupon(memberId);

        assertAll(
                () -> assertThat(memberSpecificCouponResponses.get(0).couponId()).isEqualTo(couponTokenDiscount),
                () -> assertThat(memberSpecificCouponResponses.get(0).couponName()).isEqualTo(couponNameDiscount),
                () -> assertThat(memberSpecificCouponResponses.get(0).discountValue()).isEqualTo(discountValue)
        );
    }

    @DisplayName("존재하지 않은 회원이 가진 쿠폰 목록 조회시 에러")
    @Test
    void NoneMemberGetCouponList() {
        memberRepository.save(new Member(memberId));
        couponUseService.couponPublish(memberId, couponTokenDiscount);

        assertThatThrownBy(() -> couponUseService.getMemberCoupon(noneMemberId))
                .isInstanceOf(IllegalArgumentException.class);
    }
}
