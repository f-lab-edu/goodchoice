package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.application.CouponIssueCommand;
import com.flab.goodchoicecoupon.application.CouponQuery;
import com.flab.goodchoicecoupon.domain.Coupon;
import com.flab.goodchoicecoupon.domain.CouponIssue;
import com.flab.goodchoicecoupon.domain.CouponType;
import com.flab.goodchoicecoupon.domain.State;
import com.flab.goodchoiceapi.coupon.dto.CouponUsedCancelInfoResponse;
import com.flab.goodchoiceapi.coupon.dto.CouponUsedInfoResponse;
import com.flab.goodchoicecoupon.exception.CouponException;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponIssueCommand;
import com.flab.goodchoicecoupon.infrastructure.FakeCouponQuery;
import com.flab.goodchoiceapi.coupon.infrastructure.FakeMemberCommand;
import com.flab.goodchoiceapi.member.application.MemberCommand;
import com.flab.goodchoiceapi.member.exception.MemberException;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.*;
import com.flab.goodchoicemember.domain.model.Member;
import com.flab.goodchoicemember.infrastructure.repositories.InMemoryMemberRepository;
import com.flab.goodchoicemember.infrastructure.repositories.MemberRepository;
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

    private MemberRepository memberRepository;
    private CouponUseService couponUseService;
    private CouponRepository couponRepository;
    private CouponIssueRepository couponIssueRepository;
    private CouponUseHistoryRepository couponUseHistoryEntityRepository;

    private MemberCommand memberCommand;
    private CouponQuery couponQuery;
    private CouponIssueCommand couponIssueCommand;

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
        couponIssueRepository = new InMemoryCouponIssueRepository();
        couponUseHistoryEntityRepository = new InMemoryCouponUseHistoryEntityRepository();

        memberCommand = new FakeMemberCommand(memberRepository);
        couponQuery = new FakeCouponQuery(couponRepository);
        couponIssueCommand = new FakeCouponIssueCommand(couponIssueRepository);

        couponUseService = new FakeCouponUseService(memberRepository, couponIssueRepository, couponUseHistoryEntityRepository).createCouponUseService();

        member = memberCommand.save(new Member(memberId));

        couponDiscountEntity = createCouponEntity(couponIdDiscount, couponTokenDiscount, couponNameDiscount, stockDiscount, CouponType.DISCOUNT, discountValue, State.ACTIVITY);

        couponDiscount = couponQuery.getCoupon(couponIdDiscount);

        couponDeductionEntity = createCouponEntity(couponIdDeduction, couponTokenDeduction, couponNameDeduction, stockDeduction, CouponType.DEDUCTION, deductionValue, State.ACTIVITY);

        couponDeduction = couponQuery.getCoupon(couponIdDeduction);
    }

    @DisplayName("쿠폰 사용-금액 할인")
    @ParameterizedTest
    @CsvSource(value = {"10000:1000:9000", "15000:1500:13500", "20000:2000:18000"}, delimiter = ':')
    void couponUsedDiscount(int prePrice, int discountPrice, int result) {
        couponIssueCommand.save(new CouponIssue(1L, CouponPublishToken, memberId, couponDiscount, false));

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
        couponIssueCommand.save(new CouponIssue(1L, CouponPublishToken, memberId, couponDeduction, false));

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
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("존재하지 않은 쿠폰 사용시 에러")
    @Test
    void noneCouponUsedDeduction() {
        assertThatThrownBy(() -> couponUseService.useCoupon(memberId, UUID.randomUUID(), 10000))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("가지고 있지 않은 쿠폰 사용시 에러")
    @Test
    void memberNotCouponUsedDeduction() {
        couponIssueCommand.save(new CouponIssue(1L, CouponPublishToken, memberId, couponDiscount, false));

        assertThatThrownBy(() -> couponUseService.useCoupon(memberId, couponTokenDeduction, 10000))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("쿠폰 사용 취소-금액 할인")
    @ParameterizedTest
    @CsvSource(value = {"9000:10000", "13500:15000", "18000:20000"}, delimiter = ':')
    void couponUsedCancelDiscount(int prePrice, int result) {
        couponIssueCommand.save(new CouponIssue(1L, CouponPublishToken, memberId, couponDiscount, false));

        couponUseService.useCoupon(memberId, CouponPublishToken, prePrice);

        CouponUsedCancelInfoResponse couponUsedCancelInfoResponse = couponUseService.usedCouponCancel(memberId, CouponPublishToken, prePrice);

        assertThat(couponUsedCancelInfoResponse.resultPrice()).isEqualTo(result);
    }

    @DisplayName("쿠폰 사용 취소-금액 차감")
    @ParameterizedTest
    @CsvSource(value = {"0:10000", "5000:15000", "10000:20000"}, delimiter = ':')
    void couponUsedCancelDeduction(int prePrice, int result) {
        couponIssueCommand.save(new CouponIssue(1L, CouponPublishToken, memberId, couponDeduction, false));

        couponUseService.useCoupon(memberId, CouponPublishToken, prePrice);

        CouponUsedCancelInfoResponse couponUsedCancelInfoResponse = couponUseService.usedCouponCancel(memberId, CouponPublishToken, prePrice);

        assertThat(couponUsedCancelInfoResponse.resultPrice()).isEqualTo(result);
    }

    @DisplayName("존재하지 않은 회원이 쿠폰 취소시 에러")
    @Test
    void noneMemberCouponUsedCancelDiscount() {
        assertThatThrownBy(() -> couponUseService.usedCouponCancel(noneMemberId, couponTokenDiscount, 10000))
                .isInstanceOf(MemberException.class);
    }

    @DisplayName("존재하지 않은 쿠폰 취소시 에러")
    @Test
    void noneCouponUsedCancelDiscount() {
        assertThatThrownBy(() -> couponUseService.usedCouponCancel(memberId, UUID.randomUUID(), 10000))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("가지고 있지 않은 쿠폰 취소시 에러")
    @Test
    void memberNotCouponUsedCancelDiscount() {
        couponIssueCommand.save(new CouponIssue(1L, CouponPublishToken, memberId, couponDiscount, false));

        assertThatThrownBy(() -> couponUseService.usedCouponCancel(memberId, couponTokenDeduction, 10000))
                .isInstanceOf(CouponException.class);
    }

    @DisplayName("사용하지 않은 쿠폰 취소시 에러")
    @Test
    void notUsedCouponCancelDiscount() {
        couponIssueCommand.save(new CouponIssue(1L, UUID.randomUUID(), memberId, couponDiscount, false));

        assertThatThrownBy(() -> couponUseService.usedCouponCancel(memberId, couponTokenDiscount, 10000))
                .isInstanceOf(CouponException.class);
    }

    private CouponEntity createCouponEntity(Long id, UUID couponToken, String couponName, int stock, CouponType couponType, int discountValue, State state) {
        couponDiscountEntity = new CouponEntity(id, couponToken, couponName, stock, couponType, discountValue, state);
        return couponRepository.save(couponDiscountEntity);
    }
}
