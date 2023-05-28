package com.flab.goodchoice.coupon.domain;

import com.flab.goodchoice.coupon.infrastructure.entity.CouponEntity;
import com.flab.goodchoice.coupon.infrastructure.entity.MemberEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponUseHistory {
    private Long id;
    private MemberEntity member;
    private CouponEntity couponEntity;
    private int price;
    private int discountPrice;
    private UseState useState;
    private LocalDateTime createdAt;

    public CouponUseHistory(MemberEntity member, CouponEntity couponEntity, int price, int discountPrice, UseState useState) {
        this.member = member;
        this.couponEntity = couponEntity;
        this.price = price;
        this.discountPrice = discountPrice;
        this.useState = useState;
    }

    @Builder
    public CouponUseHistory(Long id, MemberEntity member, CouponEntity couponEntity, int price, int discountPrice, UseState useState, LocalDateTime createdAt) {
        this(member, couponEntity, price, discountPrice, useState);
        this.id = id;
        this.createdAt = createdAt;
    }

    public void cancel() {
        this.useState = UseState.CANCEL;
    }
}
