package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponType;
import com.flab.goodchoice.coupon.domain.Member;
import com.flab.goodchoice.coupon.domain.State;
import com.flab.goodchoice.coupon.dto.MemberSpecificCouponResponse;
import com.flab.goodchoice.coupon.exception.CouponException;
import com.flab.goodchoice.coupon.exception.MemberException;
import com.flab.goodchoice.coupon.infrastructure.*;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertAll;

class CouponPublishServiceTest {

    private MemberRepository memberRepository;
    private CouponPublishService couponPublishService;
    private CouponRepository couponRepository;
    private CouponPublishRepository couponPublishRepository;

    private MemberQuery memberQuery;
    private MemberCommand memberCommand;
    private CouponQuery couponQuery;
    private CouponCommand couponCommand;
    private CouponPublishQuery couponPublishQuery;
    private CouponPublishCommand couponPublishCommand;
    private AppliedUserRepository appliedUserRepository;

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

    CouponEntity couponDiscountEntity;
    Coupon couponDiscount;
    CouponEntity couponDeductionEntity;
    Coupon couponDeduction;

    @BeforeEach
    void setUp() {
        memberRepository = new InMemoryMemberRepository();
        couponRepository = new InMemoryCouponRepository();
        couponPublishRepository = new InMemoryCouponPublishRepository();

        memberQuery = new FakeMemberQuery(memberRepository);
        memberCommand = new FakeMemberCommand(memberRepository);
        couponQuery = new FakeCouponQuery(couponRepository);
        couponCommand = new FakeCouponCommand(couponRepository);
        couponPublishQuery = new FakeCouponPublishQuery(couponPublishRepository);
        couponPublishCommand = new FakeCouponPublishCommand(couponPublishRepository);
        appliedUserRepository = new FakeAppliedUserRepository();

        couponPublishService = new CouponPublishService(memberQuery, couponQuery, couponCommand, couponPublishQuery, couponPublishCommand, appliedUserRepository);

        member = memberCommand.save(new Member(memberId));

        couponDiscountEntity = new CouponEntity(couponIdDiscount, couponTokenDiscount, couponNameDiscount, stockDiscount, CouponType.DISCOUNT, discountValue, State.ACTIVITY);
        couponRepository.save(couponDiscountEntity);

        couponDiscount = couponQuery.findById(couponIdDiscount);

        couponDeductionEntity = new CouponEntity(couponIdDeduction, couponTokenDeduction, couponNameDeduction, stockDeduction, CouponType.DEDUCTION, deductionValue, State.ACTIVITY);
        couponRepository.save(couponDeductionEntity);

        couponDeduction = couponQuery.findById(couponIdDeduction);
    }

    @DisplayName("회원별 쿠폰 등록")
    @Test
    void couponPublish() {
        couponPublishService.createCouponPublish(memberId, couponTokenDiscount);

        Coupon result = couponQuery.findByCouponToken(couponTokenDiscount);

        assertThat(result.getStock()).isEqualTo(99);
        assertThat(couponPublishRepository.countByCouponEntityId(couponDiscount.getId())).isEqualTo(1);
    }

    @DisplayName("한 계정당 하나의 쿠폰만 등록 가능 중복 등록시 에러")
    @Test
    void oneMemberOneCouponPublish() {
        couponPublishService.createCouponPublish(memberId, couponTokenDiscount);

        assertThatThrownBy(() -> couponPublishService.createCouponPublish(memberId, couponTokenDiscount))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("존재하지 않은 회원 쿠폰 등록시 에러")
    @Test
    void noneMemberCouponPublish() {
        assertThatThrownBy(() -> couponPublishService.createCouponPublish(noneMemberId, couponTokenDiscount))
                .isInstanceOf(MemberException.class);
    }

    @DisplayName("모두 소진된 쿠폰을 회원이 등록하면 에러")
    @Test
    void couponCountZeroPublish() {
        CouponEntity couponEntity = new CouponEntity(3L, UUID.randomUUID(), couponNameDeduction, 0, CouponType.DEDUCTION, deductionValue, State.ACTIVITY);
        couponRepository.save(couponEntity);

        assertThatThrownBy(() -> couponPublishService.createCouponPublish(memberId, couponEntity.getCouponToken()))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("회원이 가진 쿠폰 목록 조회")
    @Test
    void memberGetCouponList() {
        couponPublishService.createCouponPublish(memberId, couponTokenDiscount);

        List<MemberSpecificCouponResponse> memberSpecificCouponResponses = couponPublishService.getMemberCoupon(memberId);

        assertAll(
                () -> assertThat(memberSpecificCouponResponses.get(0).couponId()).isEqualTo(couponTokenDiscount),
                () -> assertThat(memberSpecificCouponResponses.get(0).couponName()).isEqualTo(couponNameDiscount),
                () -> assertThat(memberSpecificCouponResponses.get(0).discountValue()).isEqualTo(discountValue)
        );
    }

    @DisplayName("쿠폰을 가지지 않은 회원이 쿠폰 조회시 빈 목록 리턴")
    @Test
    void NoneMemberGetCouponList() {
        couponPublishService.createCouponPublish(memberId, couponTokenDiscount);

        List<MemberSpecificCouponResponse> result = couponPublishService.getMemberCoupon(noneMemberId);

        assertThat(result.size()).isEqualTo(0);
    }
}
