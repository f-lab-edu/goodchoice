package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.*;
import com.flab.goodchoice.coupon.dto.CouponUsedCancelInfoResponse;
import com.flab.goodchoice.coupon.dto.CouponUsedInfoResponse;
import com.flab.goodchoice.coupon.dto.MemberSpecificCouponResponse;
import com.flab.goodchoice.coupon.infrastructure.*;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.*;
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

    private MemberRepository memberRepository;
    private CouponUseService couponUseService;
    private CouponRepository couponRepository;
    private CouponPublishRepository couponPublishRepository;
    private CouponUseHistoryRepository couponUseHistoryEntityRepository;

    private MemberQuery memberQuery;
    private MemberCommand memberCommand;
    private CouponQuery couponQuery;
    private CouponCommand couponCommand;
    private CouponPublishQuery couponPublishQuery;
    private CouponPublishCommand couponPublishCommand;
    private CouponUseHistoryQuery couponUseHistoryQuery;
    private CouponUseHistoryCommand couponUseHistoryCommand;

    Member member;
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

    CouponEntity couponDiscountEntity;
    Coupon couponDiscount;
    CouponEntity couponDeductionEntity;
    Coupon couponDeduction;

    @BeforeEach
    void setUp() {
        memberRepository = new InMemoryMemberRepository();
        couponRepository = new InMemoryCouponRepository();
        couponPublishRepository = new InMemoryCouponPublishRepository();
        couponUseHistoryEntityRepository = new InMemoryCouponUseHistoryEntityRepository();

        memberQuery = new FakeMemberQuery(memberRepository);
        memberCommand = new FakeMemberCommand(memberRepository);
        couponQuery = new FakeCouponQuery(couponRepository);
        couponCommand = new FakeCouponCommand(couponRepository);
        couponPublishQuery = new FakeCouponPublishQuery(couponPublishRepository);
        couponPublishCommand = new FakeCouponPublishCommand(couponPublishRepository);
        couponUseHistoryQuery = new FakeCouponUseHistoryQuery(couponUseHistoryEntityRepository);
        couponUseHistoryCommand = new FakeCouponUseHistoryCommand(couponUseHistoryEntityRepository);

        couponUseService = new CouponUseService(memberQuery, couponQuery, couponCommand, couponPublishQuery, couponPublishCommand, couponUseHistoryQuery, couponUseHistoryCommand);

        member = memberCommand.save(new Member(memberId));

        couponDiscountEntity = new CouponEntity(couponIdDiscount, couponTokenDiscount, couponNameDiscount, stockDiscount, CouponType.DISCOUNT, discountValue, State.ACTIVITY);
        couponRepository.save(couponDiscountEntity);

        couponDiscount = couponQuery.findById(couponIdDiscount);

        couponDeductionEntity = new CouponEntity(couponIdDeduction, couponTokenDeduction, couponNameDeduction, stockDeduction, CouponType.DEDUCTION, deductionValue, State.ACTIVITY);
        couponRepository.save(couponDeductionEntity);

        couponDeduction = couponQuery.findById(couponIdDeduction);
    }

    @DisplayName("쿠폰 사용-금액 할인")
    @ParameterizedTest
    @CsvSource(value = {"10000:1000:9000", "15000:1500:13500", "20000:2000:18000"}, delimiter = ':')
    void couponUsedDiscount(int prePrice, int discountPrice, int result) {
        couponPublishCommand.save(new CouponPublish(1L, CouponPublishToken, member, couponDiscount, false));

        CouponUsedInfoResponse couponUsedInfoResponse = couponUseService.useCoupon(memberId, CouponPublishToken, prePrice);

        assertAll(
                () -> assertThat(couponUsedInfoResponse.discountPrice()).isEqualTo(discountPrice),
                () -> assertThat(couponUsedInfoResponse.resultPrice()).isEqualTo(result)
        );
    }

    @DisplayName("쿠폰 사용-금액 차감")
    @ParameterizedTest
    @CsvSource(value = {"10000:10000:0", "15000:10000:5000", "20000:10000:10000"}, delimiter = ':')
    void couponUsedDeduction(int prePrice, int discountPrice, int result) {
        couponPublishCommand.save(new CouponPublish(1L, CouponPublishToken, member, couponDeduction, false));

        CouponUsedInfoResponse couponUsedInfoResponse = couponUseService.useCoupon(memberId, CouponPublishToken, prePrice);

        assertAll(
                () -> assertThat(couponUsedInfoResponse.discountPrice()).isEqualTo(discountPrice),
                () -> assertThat(couponUsedInfoResponse.resultPrice()).isEqualTo(result)
        );
    }

    @DisplayName("존재하지 않은 회원이 쿠폰 사용시 에러")
    @Test
    void noneMemberCouponUsedDeduction() {
        assertThatThrownBy(() -> couponUseService.useCoupon(noneMemberId, couponTokenDeduction, 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("존재하지 않은 쿠폰 사용시 에러")
    @Test
    void noneCouponUsedDeduction() {
        assertThatThrownBy(() -> couponUseService.useCoupon(memberId, UUID.randomUUID(), 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가지고 있지 않은 쿠폰 사용시 에러")
    @Test
    void memberNotCouponUsedDeduction() {
        couponPublishCommand.save(new CouponPublish(1L, CouponPublishToken, member, couponDiscount, false));

        assertThatThrownBy(() -> couponUseService.useCoupon(memberId, couponTokenDeduction, 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("쿠폰 사용 취소-금액 할인")
    @ParameterizedTest
    @CsvSource(value = {"9000:10000", "13500:15000", "18000:20000"}, delimiter = ':')
    void couponUsedCancelDiscount(int prePrice, int result) {
        couponPublishCommand.save(new CouponPublish(1L, CouponPublishToken, member, couponDiscount, false));

        couponUseService.useCoupon(memberId, CouponPublishToken, prePrice);

        CouponUsedCancelInfoResponse couponUsedCancelInfoResponse = couponUseService.usedCouponCancel(memberId, CouponPublishToken, prePrice);

        assertThat(couponUsedCancelInfoResponse.resultPrice()).isEqualTo(result);
    }

    @DisplayName("쿠폰 사용 취소-금액 차감")
    @ParameterizedTest
    @CsvSource(value = {"0:10000", "5000:15000", "10000:20000"}, delimiter = ':')
    void couponUsedCancelDeduction(int prePrice, int result) {
        couponPublishCommand.save(new CouponPublish(1L, CouponPublishToken, member, couponDeduction, false));

        couponUseService.useCoupon(memberId, CouponPublishToken, prePrice);

        CouponUsedCancelInfoResponse couponUsedCancelInfoResponse = couponUseService.usedCouponCancel(memberId, CouponPublishToken, prePrice);

        assertThat(couponUsedCancelInfoResponse.resultPrice()).isEqualTo(result);
    }

    @DisplayName("존재하지 않은 회원이 쿠폰 취소시 에러")
    @Test
    void noneMemberCouponUsedCancelDiscount() {
        assertThatThrownBy(() -> couponUseService.usedCouponCancel(noneMemberId, couponTokenDiscount, 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("존재하지 않은 쿠폰 취소시 에러")
    @Test
    void noneCouponUsedCancelDiscount() {
        assertThatThrownBy(() -> couponUseService.usedCouponCancel(memberId, UUID.randomUUID(), 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("가지고 있지 않은 쿠폰 취소시 에러")
    @Test
    void memberNotCouponUsedCancelDiscount() {
        couponPublishCommand.save(new CouponPublish(1L, CouponPublishToken, member, couponDiscount, false));

        assertThatThrownBy(() -> couponUseService.usedCouponCancel(memberId, couponTokenDeduction, 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("사용하지 않은 쿠폰 취소시 에러")
    @Test
    void notUsedCouponCancelDiscount() {
        couponPublishCommand.save(new CouponPublish(1L, UUID.randomUUID(), member, couponDiscount, false));

        assertThatThrownBy(() -> couponUseService.usedCouponCancel(memberId, couponTokenDiscount, 10000))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원별 쿠폰 등록")
    @Test
    void couponPublish() {
        couponUseService.createCouponPublish(memberId, couponTokenDiscount);

        Coupon result = couponQuery.findByCouponToken(couponTokenDiscount);

        assertThat(result.getStock()).isEqualTo(99);
        assertThat(couponPublishRepository.countByCouponEntityId(couponDiscount.getId())).isEqualTo(1);
    }

    @DisplayName("존재하지 않은 회원 쿠폰 등록시 에러")
    @Test
    void noneMemberCouponPublish() {
        assertThatThrownBy(() -> couponUseService.createCouponPublish(noneMemberId, couponTokenDiscount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("모두 소진된 쿠폰을 회원이 등록하면 에러")
    @Test
    void couponCountZeroPublish() {
        CouponEntity couponEntity = new CouponEntity(3L, UUID.randomUUID(), couponNameDeduction, 0, CouponType.DEDUCTION, deductionValue, State.ACTIVITY);
        couponRepository.save(couponEntity);

        assertThatThrownBy(() -> couponUseService.createCouponPublish(memberId, couponEntity.getCouponToken()))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @DisplayName("회원이 가진 쿠폰 목록 조회")
    @Test
    void memberGetCouponList() {
        couponUseService.createCouponPublish(memberId, couponTokenDiscount);

        List<MemberSpecificCouponResponse> memberSpecificCouponResponses = couponUseService.getMemberCoupon(memberId);

        assertAll(
                () -> assertThat(memberSpecificCouponResponses.get(0).couponId()).isEqualTo(couponTokenDiscount),
                () -> assertThat(memberSpecificCouponResponses.get(0).couponName()).isEqualTo(couponNameDiscount),
                () -> assertThat(memberSpecificCouponResponses.get(0).discountValue()).isEqualTo(discountValue)
        );
    }

    @DisplayName("쿠폰을 가지지 않은 회원이 쿠폰 조회시 빈 목록 리턴")
    @Test
    void NoneMemberGetCouponList() {
        couponUseService.createCouponPublish(memberId, couponTokenDiscount);

        List<MemberSpecificCouponResponse> result = couponUseService.getMemberCoupon(noneMemberId);

        assertThat(result.size()).isEqualTo(0);
    }
}
