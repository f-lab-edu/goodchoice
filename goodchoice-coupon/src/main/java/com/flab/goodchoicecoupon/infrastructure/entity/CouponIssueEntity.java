package com.flab.goodchoicecoupon.infrastructure.entity;

import com.flab.goodchoicecoupon.domain.CouponIssue;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "coupon_issue")
public class CouponIssueEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_issue_token", columnDefinition = "BINARY(16)", nullable = false)
    private UUID couponIssueToken;

    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne
    @JoinColumn(name = "coupon_id")
    private CouponEntity couponEntity;

    @Column(name = "used_yn", nullable = false)
    private boolean usedYn;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "used_at")
    private LocalDateTime usedAt;

    public CouponIssueEntity(UUID couponIssueToken, Long memberId, CouponEntity couponEntity, boolean usedYn) {
        this.couponIssueToken = couponIssueToken;
        this.memberId = memberId;
        this.couponEntity = couponEntity;
        this.usedYn = usedYn;
    }

    public CouponIssueEntity(Long id, UUID couponIssueToken, Long memberId, CouponEntity couponEntity, boolean usedYn) {
        this(couponIssueToken, memberId, couponEntity, usedYn);
        this.id = id;
    }

    @Builder
    public CouponIssueEntity(Long id, UUID couponIssueToken, Long memberId, CouponEntity couponEntity, boolean usedYn, LocalDateTime createdAt, LocalDateTime usedAt) {
        this(id, couponIssueToken, memberId, couponEntity, usedYn);
        this.createdAt = createdAt;
        this.usedAt = usedAt;
    }

    public CouponIssue toCouponIssue() {
        return CouponIssue.builder()
                .id(getId())
                .couponIssueToken(getCouponIssueToken())
                .memberId(getMemberId())
                .coupon(getCouponEntity().toCoupon())
                .usedYn(isUsedYn())
                .createdAt(getCreatedAt())
                .usedAt(getUsedAt())
                .coupon(getCouponEntity().toCoupon())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CouponIssueEntity that)) return false;
        return Objects.equals(couponIssueToken, that.couponIssueToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couponIssueToken);
    }
}
