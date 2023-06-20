package com.flab.goodchoice.coupon.domain;

import com.flab.goodchoice.member.domain.model.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponPublish {
    private Long id;
    private UUID couponPublishToken;
    private Member member;
    private Coupon coupon;
    private boolean usedYn;
    private LocalDateTime createdAt;
    private LocalDateTime usedAt;

    public CouponPublish(UUID couponPublishToken, Member member, Coupon coupon, boolean usedYn) {
        this.couponPublishToken = couponPublishToken;
        this.member = member;
        this.coupon = coupon;
        this.usedYn = usedYn;
    }

    public CouponPublish(Long id, UUID couponPublishToken, Member member, Coupon coupon, boolean usedYn) {
        this(couponPublishToken, member, coupon, usedYn);
        this.id = id;
    }

    @Builder
    public CouponPublish(Long id, UUID couponPublishToken, Member member, Coupon coupon, boolean usedYn, LocalDateTime createdAt, LocalDateTime usedAt) {
        this(couponPublishToken, member, coupon, usedYn);
        this.id = id;
        this.createdAt = createdAt;
        this.usedAt = usedAt;
    }

    public void use() {
        this.usedYn = true;
        this.usedAt = LocalDateTime.now();
    }

    public void cancel() {
        if (!this.usedYn) {
            throw new IllegalArgumentException("사용한 쿠폰만 취소 할 수 있습니다.");
        }
        this.usedYn = false;
    }
}
