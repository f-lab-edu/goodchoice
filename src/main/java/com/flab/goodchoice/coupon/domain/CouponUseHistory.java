package com.flab.goodchoice.coupon.domain;

import com.flab.goodchoice.member.domain.model.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponUseHistory {
    private Long id;
    private Member member;
    private Coupon coupon;
    private int price;
    private int discountPrice;
    private UseState useState;
    private LocalDateTime createdAt;

    public CouponUseHistory(Member member, Coupon coupon, int price, int discountPrice, UseState useState) {
        this.member = member;
        this.coupon = coupon;
        this.price = price;
        this.discountPrice = discountPrice;
        this.useState = useState;
    }

    @Builder
    public CouponUseHistory(Long id, Member member, Coupon coupon, int price, int discountPrice, UseState useState, LocalDateTime createdAt) {
        this(member, coupon, price, discountPrice, useState);
        this.id = id;
        this.createdAt = createdAt;
    }

    public void cancel() {
        this.useState = UseState.CANCEL;
    }
}
