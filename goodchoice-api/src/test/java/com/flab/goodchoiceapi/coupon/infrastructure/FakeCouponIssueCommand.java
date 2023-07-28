package com.flab.goodchoiceapi.coupon.infrastructure;

import com.flab.goodchoicecoupon.application.CouponIssueCommand;
import com.flab.goodchoicecoupon.domain.CouponIssue;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueRepository;

public class FakeCouponIssueCommand implements CouponIssueCommand {

    private final CouponIssueRepository couponIssueRepository;

    public FakeCouponIssueCommand(CouponIssueRepository couponIssueRepository) {
        this.couponIssueRepository = couponIssueRepository;
    }

    @Override
    public CouponIssue save(CouponIssue couponPublish) {
        CouponIssueEntity couponIssueEntity = CouponIssueEntity.builder()
                .couponIssueToken(couponPublish.getCouponIssueToken())
                .memberId(couponPublish.getMemberId())
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
                .memberId(couponPublish.getMemberId())
                .couponEntity(CouponEntity.of(couponPublish.getCoupon()))
                .usedYn(couponPublish.isUsedYn())
                .createdAt(couponPublish.getCreatedAt())
                .usedAt(couponPublish.getUsedAt())
                .build();

        couponIssueRepository.save(couponIssueEntity);
    }
}
