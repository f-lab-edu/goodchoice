package com.flab.goodchoice.coupon.infrastructure.entity;

import com.flab.goodchoice.coupon.domain.CouponIssue;
import com.flab.goodchoice.member.infrastructure.entity.MemberEntity;
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

    @ManyToOne
    @JoinColumn(name = "member_id")
    private MemberEntity memberEntity;

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

    public CouponIssueEntity(UUID couponIssueToken, MemberEntity memberEntity, CouponEntity couponEntity, boolean usedYn) {
        this.couponIssueToken = couponIssueToken;
        this.memberEntity = memberEntity;
        this.couponEntity = couponEntity;
        this.usedYn = usedYn;
    }

    public CouponIssueEntity(Long id, UUID couponIssueToken, MemberEntity memberEntity, CouponEntity couponEntity, boolean usedYn) {
        this(couponIssueToken, memberEntity, couponEntity, usedYn);
        this.id = id;
    }

    @Builder
    public CouponIssueEntity(Long id, UUID couponIssueToken, MemberEntity memberEntity, CouponEntity couponEntity, boolean usedYn, LocalDateTime createdAt, LocalDateTime usedAt) {
        this(id, couponIssueToken, memberEntity, couponEntity, usedYn);
        this.createdAt = createdAt;
        this.usedAt = usedAt;
    }

    public CouponIssue toCouponIssue() {
        return CouponIssue.builder()
                .id(getId())
                .couponIssueToken(getCouponIssueToken())
                .member(getMemberEntity().toMember())
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
