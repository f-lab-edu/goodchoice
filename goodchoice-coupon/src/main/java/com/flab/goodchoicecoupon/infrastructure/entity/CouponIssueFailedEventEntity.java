package com.flab.goodchoicecoupon.infrastructure.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "coupon_issue_failed_event")
public class CouponIssueFailedEventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private CouponEntity couponEntity;

    @Column(name = "restored_yn", nullable = false)
    private boolean restoredYn;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Builder
    public CouponIssueFailedEventEntity(Long memberId, CouponEntity couponEntity, boolean restoredYn, LocalDateTime createdAt, LocalDateTime usedAt) {
        this.memberId = memberId;
        this.couponEntity = couponEntity;
        this.restoredYn = restoredYn;
        this.createdAt = createdAt;
        this.usedAt = usedAt;
    }
}
