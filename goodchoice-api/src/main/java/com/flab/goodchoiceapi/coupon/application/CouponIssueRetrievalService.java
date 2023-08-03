package com.flab.goodchoiceapi.coupon.application;

import com.flab.goodchoicecoupon.application.CouponIssueChecker;
import com.flab.goodchoicecoupon.application.CouponIssueQuery;
import com.flab.goodchoicecoupon.domain.CouponIssue;
import com.flab.goodchoiceapi.coupon.dto.MemberSpecificCouponResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
public class CouponIssueRetrievalService {

    private final CouponIssueQuery couponIssueQuery;
    private final CouponIssueChecker couponIssueExistChecker;

    public CouponIssueRetrievalService(CouponIssueQuery couponIssueQuery, CouponIssueChecker couponIssueExistChecker) {
        this.couponIssueQuery = couponIssueQuery;
        this.couponIssueExistChecker = couponIssueExistChecker;
    }

    public List<MemberSpecificCouponResponse> getIssuedMemberCoupon(Long memberId) {
        List<CouponIssue> couponIssues = couponIssueQuery.getCouponIssues(memberId);
        return couponIssues.stream()
                .map(couponIssue -> MemberSpecificCouponResponse.of(couponIssue.getCoupon()))
                .toList();
    }

    public void duplicateCouponIssueCheck(Long memberId, UUID couponToken) {
        couponIssueExistChecker.duplicateCouponIssueCheck(memberId, couponToken);
    }
}
