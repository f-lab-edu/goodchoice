package com.flab.goodchoice.coupon.infrastructure;

import com.flab.goodchoice.coupon.application.CouponIssueCommand;
import com.flab.goodchoice.coupon.domain.CouponIssue;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoice.coupon.infrastructure.entity.CouponIssueEntity;
import com.flab.goodchoice.member.infrastructure.entity.MemberEntity;
import com.flab.goodchoice.coupon.infrastructure.repositories.CouponIssueRepository;

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
