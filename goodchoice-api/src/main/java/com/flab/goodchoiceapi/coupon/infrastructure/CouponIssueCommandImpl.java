package com.flab.goodchoiceapi.coupon.infrastructure;

import com.flab.goodchoiceapi.coupon.application.CouponIssueCommand;
import com.flab.goodchoicecoupon.domain.CouponIssue;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoicecoupon.infrastructure.entity.CouponIssueEntity;
import com.flab.goodchoicecoupon.infrastructure.repositories.CouponIssueRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Component
public class CouponIssueCommandImpl implements CouponIssueCommand {

    private final CouponIssueRepository couponPublishRepository;

    public CouponIssueCommandImpl(CouponIssueRepository couponPublishRepository) {
        this.couponPublishRepository = couponPublishRepository;
    }

    @Override
    public CouponIssue save(CouponIssue couponPublish) {
        CouponIssueEntity couponPublishEntity = CouponIssueEntity.builder()
                .couponIssueToken(couponPublish.getCouponIssueToken())
                .memberId(couponPublish.getMemberId())
                .couponEntity(CouponEntity.of(couponPublish.getCoupon()))
                .usedYn(couponPublish.isUsedYn())
                .build();

        CouponIssueEntity saveCouponPublishEntity = couponPublishRepository.save(couponPublishEntity);

        return saveCouponPublishEntity.toCouponIssue();
    }

    @Override
    public void modify(CouponIssue couponIssue) {
        CouponIssueEntity couponPublishEntity = CouponIssueEntity.builder()
                .id(couponIssue.getId())
                .couponIssueToken(couponIssue.getCouponIssueToken())
                .memberId(couponIssue.getMemberId())
                .couponEntity(CouponEntity.of(couponIssue.getCoupon()))
                .usedYn(couponIssue.isUsedYn())
                .createdAt(couponIssue.getCreatedAt())
                .usedAt(couponIssue.getUsedAt())
                .build();

        couponPublishRepository.save(couponPublishEntity);
    }
}
