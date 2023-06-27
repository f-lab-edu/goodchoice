package com.flab.goodchoice.coupon.application;

import com.flab.goodchoice.coupon.domain.CouponIssue;
import com.flab.goodchoice.coupon.dto.MemberSpecificCouponResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
public class CouponIssueRetrievalService {

    private final CouponIssueQuery couponIssueQuery;

    public CouponIssueRetrievalService(CouponIssueQuery couponIssueQuery) {
        this.couponIssueQuery = couponIssueQuery;
    }

    public List<MemberSpecificCouponResponse> getMemberCoupon(Long memberId) {
        List<CouponIssue> couponIssues = couponIssueQuery.getCouponIssue(memberId);
        return couponIssues.stream()
                .map(couponIssue -> MemberSpecificCouponResponse.of(couponIssue.getCoupon()))
                .toList();
    }
}
