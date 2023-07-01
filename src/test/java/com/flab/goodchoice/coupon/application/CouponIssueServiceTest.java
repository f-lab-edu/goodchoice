package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponType;
import com.flab.goodchoice.coupon.domain.State;
import com.flab.goodchoice.coupon.exception.CouponException;
import com.flab.goodchoice.coupon.infrastructure.*;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.*;
import com.flab.goodchoice.member.application.MemberCommand;
import com.flab.goodchoice.member.application.MemberQuery;
import com.flab.goodchoice.member.domain.model.Member;
import com.flab.goodchoice.member.domain.repositories.MemberRepository;
import com.flab.goodchoice.member.exception.MemberException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CouponIssueServiceTest {

    private MemberRepository memberRepository;
    private CouponIssueService couponIssueService;
    private CouponRepository couponRepository;
    private CouponIssueRepository couponIssueRepository;

    private MemberQuery memberQuery;
    private MemberCommand memberCommand;
    private CouponQuery couponQuery;
    private CouponCommand couponCommand;
    private CouponIssueCommand couponIssueCommand;
    private AppliedUserRepository appliedUserRepository;
    private CouponIssueExistCheck couponIssueExistCheck;

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
        couponIssueRepository = new InMemoryCouponIssueRepository();

        memberQuery = new FakeMemberQuery(memberRepository);
        memberCommand = new FakeMemberCommand(memberRepository);
        couponQuery = new FakeCouponQuery(couponRepository);
        couponCommand = new FakeCouponCommand(couponRepository);
        couponIssueCommand = new FakeCouponIssueCommand(couponIssueRepository);
        appliedUserRepository = new FakeAppliedUserRepository();
        couponIssueExistCheck = new FakeCouponIssueExistCheck(couponIssueRepository);

        couponIssueService = new CouponIssueService(memberQuery, couponQuery, couponCommand, couponIssueCommand, couponIssueExistCheck,  appliedUserRepository);

        member = memberCommand.save(new Member(memberId));

        couponDiscountEntity = new CouponEntity(couponIdDiscount, couponTokenDiscount, couponNameDiscount, stockDiscount, CouponType.DISCOUNT, discountValue, State.ACTIVITY);
        couponRepository.save(couponDiscountEntity);

        couponDiscount = couponQuery.getCoupon(couponIdDiscount);

        couponDeductionEntity = new CouponEntity(couponIdDeduction, couponTokenDeduction, couponNameDeduction, stockDeduction, CouponType.DEDUCTION, deductionValue, State.ACTIVITY);
        couponRepository.save(couponDeductionEntity);

        couponDeduction = couponQuery.getCoupon(couponIdDeduction);
    }

    @DisplayName("회원별 쿠폰 등록")
    @Test
    void couponPublish() {
        couponIssueService.couponIssue(memberId, couponTokenDiscount);

        Coupon result = couponQuery.getCoupon(couponTokenDiscount);

        assertThat(result.getStock()).isEqualTo(99);
        assertThat(couponIssueRepository.countByCouponEntityId(couponDiscount.getId())).isEqualTo(1);
    }

    @DisplayName("한 계정당 하나의 쿠폰만 등록 가능 중복 등록시 에러")
    @Test
    void oneMemberOneCouponPublish() {
        couponIssueService.couponIssue(memberId, couponTokenDiscount);

        assertThatThrownBy(() -> couponIssueService.couponIssue(memberId, couponTokenDiscount))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("존재하지 않은 회원 쿠폰 등록시 에러")
    @Test
    void noneMemberCouponPublish() {
        assertThatThrownBy(() -> couponIssueService.couponIssue(noneMemberId, couponTokenDiscount))
                .isInstanceOf(MemberException.class);
    }

    @DisplayName("모두 소진된 쿠폰을 회원이 등록하면 에러")
    @Test
    void couponCountZeroPublish() {
        CouponEntity couponEntity = new CouponEntity(3L, UUID.randomUUID(), couponNameDeduction, 0, CouponType.DEDUCTION, deductionValue, State.ACTIVITY);
        couponRepository.save(couponEntity);

        assertThatThrownBy(() -> couponIssueService.couponIssue(memberId, couponEntity.getCouponToken()))
                .isInstanceOf(CouponException.class);
    }
}
