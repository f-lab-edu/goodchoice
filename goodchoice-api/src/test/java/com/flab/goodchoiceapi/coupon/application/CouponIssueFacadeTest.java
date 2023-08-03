package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoiceapi.coupon.domain.CouponIssueFailedService;
import com.flab.goodchoiceapi.coupon.domain.CouponIssueService;
import com.flab.goodchoicemember.application.MemberRetrievalService;
import com.flab.goodchoicemember.domain.model.Member;
import com.flab.goodchoicemember.exception.MemberException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CouponIssueFacadeTest {

    @Mock
    private MemberRetrievalService memberRetrievalService;
    @Mock
    private CouponService couponService;
    @Mock
    private CouponIssueService couponIssueService;
    @Mock
    private CouponIssueFailedService couponIssueFailedService;

    @InjectMocks
    private CouponIssueFacade couponIssueFacade;

    @DisplayName("회원별 쿠폰 등록 성공")
    @Test
    void createCouponIssue() {
        when(memberRetrievalService.getMember(anyLong())).thenReturn(Member.builder().build());
        when(couponIssueService.couponIssue(anyLong(), any(), any())).thenReturn(true);

        boolean result = couponIssueFacade.couponIssue(1L, UUID.randomUUID());

        assertThat(result).isTrue();
    }

    @DisplayName("존재하지 않은 회원 쿠폰 등록시 에러")
    @Test
    void noneMemberCouponIssue() {
        when(memberRetrievalService.getMember(anyLong())).thenThrow(MemberException.class);

        assertThatThrownBy(() -> couponIssueFacade.couponIssue(1L, UUID.randomUUID()))
                .isInstanceOf(MemberException.class);
    }

    @DisplayName("쿠폰 등록 실패시 false 리턴")
    @Test
    void failCreateCouponIssue() {
        when(memberRetrievalService.getMember(anyLong())).thenReturn(Member.builder().build());
        when(couponIssueService.couponIssue(anyLong(), any(), any())).thenReturn(false);

        boolean result = couponIssueFacade.couponIssue(1L, UUID.randomUUID());

        assertThat(result).isFalse();
    }
}