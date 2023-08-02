package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoiceapi.coupon.domain.CouponIssueService;
import com.flab.goodchoicecoupon.domain.CouponType;
import com.flab.goodchoicecoupon.domain.State;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueFailedEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.*;
import com.flab.goodchoicemember.application.MemberCommand;
import com.flab.goodchoicemember.domain.model.Member;
import com.flab.goodchoicemember.infrastructure.FakeMemberCommand;
import com.flab.goodchoicemember.infrastructure.repositories.InMemoryMemberRepository;
import com.flab.goodchoicemember.infrastructure.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

public class CouponIssueServiceCouponIssueFailTest {

    private MemberRepository memberRepository;
    private CouponRepository couponRepository;
    private CouponIssueRepository couponIssueRepository;
    private CouponIssueFailedRepository couponIssueFailedRepository;

    private CouponIssueService couponIssueService;

    private MemberCommand memberCommand;

    Member member;
    final Long memberId = 1L;

    final Long couponIdDiscount = 1L;
    final UUID couponTokenDiscount = UUID.randomUUID();
    final String couponNameDiscount = "10%할인";
    final int stockDiscount = 100;
    final int discountValue = 10;

    CouponEntity couponDiscountEntity;

    @BeforeEach
    void setUp() {
        memberRepository = new InMemoryMemberRepository();
        couponRepository = new InMemoryCouponRepository();
        couponIssueRepository = new InMemoryCouponIssueRepository();
        couponIssueFailedRepository = new InMemoryCouponIssueFailedRepository();

        memberCommand = new FakeMemberCommand(memberRepository);

        couponIssueService = new FakeCouponIssueServiceCouponIssueFail(couponRepository, couponIssueRepository).createCouponIssueService();

        member = memberCommand.createMember(new Member(memberId));

        couponDiscountEntity = new CouponEntity(couponIdDiscount, couponTokenDiscount, couponNameDiscount, stockDiscount, CouponType.DISCOUNT, discountValue, State.ACTIVITY);
        couponRepository.save(couponDiscountEntity);
    }

    @DisplayName("회원별 쿠폰 등록 중 에러 발생시 실패한 쿠폰 정보 등록")
    @Test
    void createCouponIssueExceptionCreateFailCouponIssue() {
        couponIssueService.couponIssue(memberId, couponTokenDiscount);

        CouponIssueFailedEntity entity = couponIssueFailedRepository.findById(1L).get();

        assertThat(entity.getMemberId()).isEqualTo(memberId);
        assertThat(entity.getCouponToken()).isEqualTo(couponTokenDiscount);
    }
}
