package com.flab.goodchoice.coupon.infrastructure.entity;

import com.flab.goodchoice.coupon.domain.CouponPublish;
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
@Table(name = "coupon_publish")
public class CouponPublishEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_publish_token", columnDefinition = "BINARY(16)", nullable = false)
    private UUID couponPublishToken;

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

    public CouponPublishEntity(UUID couponPublishToken, Long memberId, CouponEntity couponEntity, boolean usedYn) {
        this.couponPublishToken = couponPublishToken;
        this.memberId = memberId;
        this.couponEntity = couponEntity;
        this.usedYn = usedYn;
    }

    public CouponPublishEntity(Long id, UUID couponPublishToken, Long memberId, CouponEntity couponEntity, boolean usedYn) {
        this(couponPublishToken, memberId, couponEntity, usedYn);
        this.id = id;
    }

    @Builder
    public CouponPublishEntity(Long id, UUID couponPublishToken, Long memberId, CouponEntity couponEntity, boolean usedYn, LocalDateTime createdAt, LocalDateTime usedAt) {
        this(id, couponPublishToken, memberId, couponEntity, usedYn);
        this.createdAt = createdAt;
        this.usedAt = usedAt;
    }

    public CouponPublish toCouponPublish() {
        return CouponPublish.builder()
                .id(getId())
                .couponPublishToken(getCouponPublishToken())
                .memberId(getMemberId())
                .couponId(getCouponEntity().getId())
                .usedYn(isUsedYn())
                .createdAt(getCreatedAt())
                .usedAt(getUsedAt())
                .coupon(getCouponEntity().toCoupon())
                .build();
    }
}
