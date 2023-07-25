package com.flab.goodchoicecoupon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponUseHistory {
    private Long id;
    private Long memberId;
    private Coupon coupon;
    private int price;
    private int discountPrice;
    private UseState useState;
    private LocalDateTime createdAt;

    public CouponUseHistory(Long memberId, Coupon coupon, int price, int discountPrice, UseState useState) {
        this.memberId = memberId;
        this.coupon = coupon;
        this.price = price;
        this.discountPrice = discountPrice;
        this.useState = useState;
    }

    @Builder
    public CouponUseHistory(Long id, Long memberId, Coupon coupon, int price, int discountPrice, UseState useState, LocalDateTime createdAt) {
        this(memberId, coupon, price, discountPrice, useState);
        this.id = id;
        this.createdAt = createdAt;
    }

    public void cancel() {
        this.useState = UseState.CANCEL;
    }
}
