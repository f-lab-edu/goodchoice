package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponType;
import com.flab.goodchoice.coupon.domain.State;
import com.flab.goodchoice.coupon.dto.MemberSpecificCouponResponse;
import com.flab.goodchoice.coupon.infrastructure.*;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.*;
import com.flab.goodchoice.member.application.MemberCommand;
import com.flab.goodchoice.member.application.MemberQuery;
import com.flab.goodchoice.member.domain.model.Member;
import com.flab.goodchoice.member.domain.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CouponIssueInfoServiceTest {

    private MemberRepository memberRepository;
    private CouponIssueService couponIssueService;
    private CouponRepository couponRepository;
    private CouponPublishRepository couponPublishRepository;

    private MemberQuery memberQuery;
    private MemberCommand memberCommand;
    private CouponQuery couponQuery;
    private CouponCommand couponCommand;
    private CouponPublishCommand couponPublishCommand;
    private AppliedUserRepository appliedUserRepository;
    private CouponPublishExistCheck couponPublishExistCheck;
    private CouponPublishQuery couponPublishQuery;

    private CouponIssueInfoService couponIssueInfoService;

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
        couponPublishCommand = new FakeCouponPublishCommand(couponPublishRepository);
        appliedUserRepository = new FakeAppliedUserRepository();
        couponPublishExistCheck = new FakeCouponPublishExistCheck(couponPublishRepository);
        couponPublishQuery = new FakeCouponPublishQuery(couponPublishRepository);

        couponIssueService = new CouponIssueService(memberQuery, couponQuery, couponCommand, couponPublishCommand, couponPublishExistCheck,  appliedUserRepository);
        couponIssueInfoService = new CouponIssueInfoService(couponPublishQuery);

        member = memberCommand.save(new Member(memberId));

        couponDiscountEntity = new CouponEntity(couponIdDiscount, couponTokenDiscount, couponNameDiscount, stockDiscount, CouponType.DISCOUNT, discountValue, State.ACTIVITY);
        couponRepository.save(couponDiscountEntity);

        couponDiscount = couponQuery.getCouponInfo(couponIdDiscount);

        couponDeductionEntity = new CouponEntity(couponIdDeduction, couponTokenDeduction, couponNameDeduction, stockDeduction, CouponType.DEDUCTION, deductionValue, State.ACTIVITY);
        couponRepository.save(couponDeductionEntity);

        couponDeduction = couponQuery.getCouponInfo(couponIdDeduction);
    }

    @DisplayName("회원이 가진 쿠폰 목록 조회")
    @Test
    void memberGetCouponList() {
        couponIssueService.couponIssuance(memberId, couponTokenDiscount);

        List<MemberSpecificCouponResponse> memberSpecificCouponResponses = couponIssueInfoService.getMemberCoupon(memberId);

        assertAll(
                () -> assertThat(memberSpecificCouponResponses.get(0).couponId()).isEqualTo(couponTokenDiscount),
                () -> assertThat(memberSpecificCouponResponses.get(0).couponName()).isEqualTo(couponNameDiscount),
                () -> assertThat(memberSpecificCouponResponses.get(0).discountValue()).isEqualTo(discountValue)
        );
    }

    @DisplayName("쿠폰을 가지지 않은 회원이 쿠폰 조회시 빈 목록 리턴")
    @Test
    void NoneMemberGetCouponList() {
        couponIssueService.couponIssuance(memberId, couponTokenDiscount);

        List<MemberSpecificCouponResponse> result = couponIssueInfoService.getMemberCoupon(noneMemberId);

        assertThat(result.size()).isEqualTo(0);
    }
}
