package com.flab.goodchoice.coupon.domain;

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
    private Long memberId;
    private Long couponId;
    private boolean usedYn;
    private LocalDateTime createdAt;
    private LocalDateTime usedAt;
    private Coupon coupon;

    public CouponPublish(UUID couponPublishToken, Long memberId, Long couponId, boolean usedYn, Coupon coupon) {
        this.couponPublishToken = couponPublishToken;
        this.memberId = memberId;
        this.couponId = couponId;
        this.usedYn = usedYn;
        this.coupon = coupon;
    }

    @Builder
    public CouponPublish(Long id, UUID couponPublishToken, Long memberId, Long couponId, boolean usedYn, LocalDateTime createdAt, LocalDateTime usedAt, Coupon coupon) {
        this(couponPublishToken, memberId, couponId, usedYn, coupon);
        this.id = id;
        this.createdAt = createdAt;
        this.usedAt = usedAt;
    }

    public void used() {
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
