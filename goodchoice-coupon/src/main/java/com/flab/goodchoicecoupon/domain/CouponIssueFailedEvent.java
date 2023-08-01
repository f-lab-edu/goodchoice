package com.flab.goodchoicecoupon.domain;

import lombok.Builder;
import lombok.Getter;
import org.springframework.util.Assert;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class CouponIssueFailedEvent {

    private final Long id;
    private final Long memberId;
    private final UUID couponToken;
    private boolean restoredYn;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;

    @Builder
    public CouponIssueFailedEvent(Long id, Long memberId, UUID couponToken, boolean restoredYn, LocalDateTime createdAt, LocalDateTime updatedAt) {
        Assert.notNull(memberId, "쿠폰 등록한 회원을 알 수가 없습니다.");
        Assert.notNull(couponToken, "해당 쿠폰을 입력해주세요.");
        Assert.notNull(restoredYn, "실패된 쿠폰 상태를 입력해주세요.");

        this.id = id;
        this.memberId = memberId;
        this.couponToken = couponToken;
        this.restoredYn = restoredYn;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
