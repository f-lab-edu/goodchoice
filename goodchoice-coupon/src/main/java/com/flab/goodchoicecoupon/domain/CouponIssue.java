package com.flab.goodchoicecoupon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponIssue {
    private Long id;
    private UUID couponIssueToken;
    private Long memberId;
    private Coupon coupon;
    private boolean usedYn;
    private LocalDateTime createdAt;
    private LocalDateTime usedAt;

    public CouponIssue(UUID couponIssueToken, Long memberId, Coupon coupon, boolean usedYn) {
        this.couponIssueToken = couponIssueToken;
        this.memberId = memberId;
        this.coupon = coupon;
        this.usedYn = usedYn;
    }

    public CouponIssue(Long id, UUID couponIssueToken, Long memberId, Coupon coupon, boolean usedYn) {
        this(couponIssueToken, memberId, coupon, usedYn);
        this.id = id;
    }

    @Builder
    public CouponIssue(Long id, UUID couponIssueToken, Long memberId, Coupon coupon, boolean usedYn, LocalDateTime createdAt, LocalDateTime usedAt) {
        this(couponIssueToken, memberId, coupon, usedYn);
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
