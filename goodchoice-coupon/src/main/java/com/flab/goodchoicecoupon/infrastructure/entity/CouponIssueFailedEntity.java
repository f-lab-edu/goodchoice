package com.flab.goodchoicecoupon.infrastructure.entity;

import com.flab.goodchoicecoupon.domain.CouponIssueFailed;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "coupon_issue_failed_event")
public class CouponIssueFailedEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "coupon_token", columnDefinition = "BINARY(16)", nullable = false)
    private UUID couponToken;

    @Column(name = "restored_yn", nullable = false)
    private boolean restoredYn;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public CouponIssueFailedEntity(Long memberId, UUID couponToken, boolean restoredYn, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.memberId = memberId;
        this.couponToken = couponToken;
        this.restoredYn = restoredYn;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    @Builder
    public CouponIssueFailedEntity(Long id, Long memberId, UUID couponToken, boolean restoredYn, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(memberId, couponToken, restoredYn, createdAt, updatedAt);
        this.id = id;
    }

    public CouponIssueFailed toCouponIssueFailedEvent() {
        return CouponIssueFailed.builder()
                .id(getId())
                .memberId(getMemberId())
                .couponToken(getCouponToken())
                .restoredYn(isRestoredYn())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }
}
