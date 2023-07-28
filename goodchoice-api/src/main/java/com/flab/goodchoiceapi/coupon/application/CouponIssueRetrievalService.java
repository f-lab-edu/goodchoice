package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.application.CouponIssueQuery;
import com.flab.goodchoicecoupon.domain.CouponIssue;
import com.flab.goodchoiceapi.coupon.dto.MemberSpecificCouponResponse;
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

    public List<MemberSpecificCouponResponse> getIssuedMemberCoupon(Long memberId) {
        List<CouponIssue> couponIssues = couponIssueQuery.getCouponIssues(memberId);
        return couponIssues.stream()
                .map(couponIssue -> MemberSpecificCouponResponse.of(couponIssue.getCoupon()))
                .toList();
    }
}
