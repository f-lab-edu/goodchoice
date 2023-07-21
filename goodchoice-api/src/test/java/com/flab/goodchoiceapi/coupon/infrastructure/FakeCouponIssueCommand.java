package com.flab.goodchoiceapi.coupon.infrastructure;

import com.flab.goodchoiceapi.coupon.application.CouponIssueCommand;
import com.flab.goodchoicecoupon.domain.CouponIssue;
import com.flab.goodchoiceapi.coupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoiceapi.coupon.infrastructure.entity.CouponIssueEntity;
import com.flab.goodchoiceapi.coupon.infrastructure.repositories.CouponIssueRepository;
import com.flab.goodchoiceapi.member.infrastructure.entity.MemberEntity;

public class FakeCouponIssueCommand implements CouponIssueCommand {

    private final CouponIssueRepository couponIssueRepository;

    public FakeCouponIssueCommand(CouponIssueRepository couponIssueRepository) {
        this.couponIssueRepository = couponIssueRepository;
    }

    @Override
    public CouponIssue save(CouponIssue couponPublish) {
        CouponIssueEntity couponIssueEntity = CouponIssueEntity.builder()
                .couponIssueToken(couponPublish.getCouponIssueToken())
                .memberEntity(MemberEntity.of(couponPublish.getMember()))
                .couponEntity(CouponEntity.of(couponPublish.getCoupon()))
                .usedYn(couponPublish.isUsedYn())
                .build();

        CouponIssueEntity saveCouponIssueEntity = couponIssueRepository.save(couponIssueEntity);

        return saveCouponIssueEntity.toCouponIssue();
    }

    @Override
    public void modify(CouponIssue couponPublish) {
        CouponIssueEntity couponIssueEntity = CouponIssueEntity.builder()
                .id(couponPublish.getId())
                .couponIssueToken(couponPublish.getCouponIssueToken())
                .memberEntity(MemberEntity.of(couponPublish.getMember()))
                .couponEntity(CouponEntity.of(couponPublish.getCoupon()))
                .usedYn(couponPublish.isUsedYn())
                .createdAt(couponPublish.getCreatedAt())
                .usedAt(couponPublish.getUsedAt())
                .build();

        couponIssueRepository.save(couponIssueEntity);
    }
}
