package com.flab.goodchoicecoupon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponIssueFailedEvent {

    private Long id;
    private Long memberId;
    private UUID couponToken;
    private boolean restoredYn;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public CouponIssueFailedEvent(Long memberId, UUID couponToken, boolean restoredYn) {
        Assert.notNull(memberId, "쿠폰 등록한 회원을 알 수가 없습니다.");
        Assert.notNull(couponToken, "해당 쿠폰을 입력해주세요.");
        Assert.notNull(restoredYn, "실패된 쿠폰 상태를 입력해주세요.");

        this.memberId = memberId;
        this.couponToken = couponToken;
        this.restoredYn = restoredYn;
    }

    public CouponIssueFailedEvent(Long memberId, UUID couponToken, boolean restoredYn, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(memberId, couponToken, restoredYn);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Builder
    public CouponIssueFailedEvent(Long id, Long memberId, UUID couponToken, boolean restoredYn, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(memberId, couponToken, restoredYn, createdAt, updatedAt);
        this.id = id;
    }
}
