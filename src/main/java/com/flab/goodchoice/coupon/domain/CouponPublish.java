package com.flab.goodchoice.coupon.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "coupon_publish")
public class CouponPublish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_publish_token", columnDefinition = "BINARY(16)", nullable = false)
    private UUID couponPublishToken;

    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;

    @Column(name = "used_yn", nullable = false)
    private boolean usedYn;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    public CouponPublish(UUID couponPublishToken, Long memberId, Coupon coupon, boolean usedYn) {
        this.couponPublishToken = couponPublishToken;
        this.memberId = memberId;
        this.coupon = coupon;
        this.usedYn = usedYn;
    }

    public CouponPublish(Long id, UUID couponPublishToken, Long memberId, Coupon coupon, boolean usedYn) {
        this(couponPublishToken, memberId, coupon, usedYn);
        this.id = id;
    }

    public void used() {
        this.usedYn = true;
        this.usedAt = LocalDateTime.now();
    }

    public void cancel() {
        this.usedYn = false;
    }
}
