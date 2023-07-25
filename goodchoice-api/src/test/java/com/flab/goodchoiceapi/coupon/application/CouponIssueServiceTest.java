package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.domain.Coupon;
import com.flab.goodchoicecoupon.domain.CouponType;
import com.flab.goodchoicecoupon.domain.State;
import com.flab.goodchoicecoupon.exception.CouponException;
import com.flab.goodchoiceapi.coupon.infrastructure.FakeCouponQuery;
import com.flab.goodchoiceapi.coupon.infrastructure.FakeMemberCommand;
import com.flab.goodchoiceapi.member.application.MemberCommand;
import com.flab.goodchoiceapi.member.exception.MemberException;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueRepository;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponRepository;
import com.flab.goodchoicecoupon.infrastructure.repositories.InMemoryCouponIssueRepository;
import com.flab.goodchoicecoupon.infrastructure.repositories.InMemoryCouponRepository;
import com.flab.goodchoicemember.domain.model.Member;
import com.flab.goodchoicemember.infrastructure.repositories.InMemoryMemberRepository;
import com.flab.goodchoicemember.infrastructure.repositories.MemberRepository;
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

    private MemberCommand memberCommand;
    private CouponQuery couponQuery;

    Member member;
    final Long memberId = 1L;
    final Long noneMemberId = 2L;

    final Long couponIdDiscount = 1L;
    final UUID couponTokenDiscount = UUID.randomUUID();
    final String couponNameDiscount = "10%할인";
    final int stockDiscount = 100;
    final int discountValue = 10;

    final String couponNameDeduction = "10000원 차감";
    final int deductionValue = 10000;

    CouponEntity couponDiscountEntity;
    Coupon couponDiscount;

    @BeforeEach
    void setUp() {
        memberRepository = new InMemoryMemberRepository();
        couponRepository = new InMemoryCouponRepository();
        couponIssueRepository = new InMemoryCouponIssueRepository();

        memberCommand = new FakeMemberCommand(memberRepository);
        couponQuery = new FakeCouponQuery(couponRepository);

        couponIssueService = new FakeCouponIssueService(memberRepository, couponRepository, couponIssueRepository).createCouponIssueService();

        member = memberCommand.save(new Member(memberId));

        couponDiscountEntity = new CouponEntity(couponIdDiscount, couponTokenDiscount, couponNameDiscount, stockDiscount, CouponType.DISCOUNT, discountValue, State.ACTIVITY);
        couponRepository.save(couponDiscountEntity);

        couponDiscount = couponQuery.getCoupon(couponIdDiscount);
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
