package com.flab.goodchoicecoupon.domain;

import com.flab.goodchoicecoupon.infrastructure.entity.CouponEntity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CouponIssueFailedEvent {

    private Long id;
    private Long memberId;
    private CouponEntity couponEntity;
    private boolean restoredYn;
    private LocalDateTime createdAt;
    private LocalDateTime usedAt;

    public CouponIssueFailedEvent(Long memberId, CouponEntity couponEntity, boolean restoredYn) {
        Assert.notNull(memberId, "쿠폰 등록한 회원을 알 수가 없습니다.");
        Assert.notNull(couponEntity, "해당 쿠폰을 입력해주세요.");
        Assert.notNull(restoredYn, "실패된 쿠폰 상태를 입력해주세요.");

        this.memberId = memberId;
        this.couponEntity = couponEntity;
        this.restoredYn = restoredYn;
    }

    @Builder
    public CouponIssueFailedEvent(Long memberId, CouponEntity couponEntity, boolean restoredYn, LocalDateTime createdAt, LocalDateTime usedAt) {
        this(memberId, couponEntity, restoredYn);
        this.createdAt = createdAt;
        this.usedAt = usedAt;
    }
}
