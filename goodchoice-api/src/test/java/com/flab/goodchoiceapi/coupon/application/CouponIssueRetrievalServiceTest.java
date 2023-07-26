package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.domain.CouponType;
import com.flab.goodchoicecoupon.domain.State;
import com.flab.goodchoiceapi.coupon.dto.MemberSpecificCouponResponse;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueRepository;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponRepository;
import com.flab.goodchoicecoupon.infrastructure.repositories.InMemoryCouponIssueRepository;
import com.flab.goodchoicecoupon.infrastructure.repositories.InMemoryCouponRepository;
import com.flab.goodchoicemember.application.MemberCommand;
import com.flab.goodchoicemember.domain.model.Member;
import com.flab.goodchoicemember.infrastructure.FakeMemberCommand;
import com.flab.goodchoicemember.infrastructure.repositories.InMemoryMemberRepository;
import com.flab.goodchoicemember.infrastructure.repositories.MemberRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class CouponIssueRetrievalServiceTest {

    private MemberRepository memberRepository;
    private CouponIssueService couponIssueService;
    private CouponRepository couponRepository;
    private CouponIssueRepository couponIssueRepository;

    private MemberCommand memberCommand;

    private CouponIssueRetrievalService couponIssueRetrievalService;

    Member member;
    final Long memberId = 1L;
    final Long noneMemberId = 2L;

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

        memberCommand = new FakeMemberCommand(memberRepository);

        couponIssueService = new FakeCouponIssueService(memberRepository, couponRepository, couponIssueRepository).createCouponIssueService();
        couponIssueRetrievalService = new FakeCouponIssueRetrievalService(couponIssueRepository).createCouponIssueRetrievalService();

        member = memberCommand.createMember(new Member(memberId));

        couponDiscountEntity = new CouponEntity(couponIdDiscount, couponTokenDiscount, couponNameDiscount, stockDiscount, CouponType.DISCOUNT, discountValue, State.ACTIVITY);
        couponRepository.save(couponDiscountEntity);
    }

    @DisplayName("회원이 가진 쿠폰 목록 조회")
    @Test
    void memberGetCouponList() {
        couponIssueService.couponIssue(memberId, couponTokenDiscount);

        List<MemberSpecificCouponResponse> memberSpecificCouponResponses = couponIssueRetrievalService.getIssuedMemberCoupon(memberId);

        assertAll(
                () -> assertThat(memberSpecificCouponResponses.get(0).couponId()).isEqualTo(couponTokenDiscount),
                () -> assertThat(memberSpecificCouponResponses.get(0).couponName()).isEqualTo(couponNameDiscount),
                () -> assertThat(memberSpecificCouponResponses.get(0).discountValue()).isEqualTo(discountValue)
        );
    }

    @DisplayName("쿠폰을 가지지 않은 회원이 쿠폰 조회시 빈 목록 리턴")
    @Test
    void NoneMemberGetCouponList() {
        couponIssueService.couponIssue(memberId, couponTokenDiscount);

        List<MemberSpecificCouponResponse> result = couponIssueRetrievalService.getIssuedMemberCoupon(noneMemberId);

        assertThat(result.size()).isEqualTo(0);
    }
}
