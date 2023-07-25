package com.flab.goodchoicecoupon.infrastructure.entity;

import com.flab.goodchoicecoupon.domain.CouponUseHistory;
import com.flab.goodchoicecoupon.domain.UseState;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "coupon_use_history")
public class CouponUseHistoryEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private CouponEntity couponEntity;

    @Column(name = "price")
    private int price;

    @Column(name = "discount_price")
    private int discountPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private UseState useState;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public CouponUseHistoryEntity(Long memberId, CouponEntity couponEntity, int price, int discountPrice, UseState useState) {
        this.memberId = memberId;
        this.couponEntity = couponEntity;
        this.price = price;
        this.discountPrice = discountPrice;
        this.useState = useState;
    }

    @Builder
    public CouponUseHistoryEntity(Long id, Long memberId, CouponEntity couponEntity, int price, int discountPrice, UseState useState, LocalDateTime createdAt) {
        this.id = id;
        this.memberId = memberId;
        this.couponEntity = couponEntity;
        this.price = price;
        this.discountPrice = discountPrice;
        this.useState = useState;
        this.createdAt = createdAt;
    }

    public CouponUseHistory toCouponUseHistory() {
        return CouponUseHistory.builder()
                .id(getId())
                .memberId(getMemberId())
                .coupon(getCouponEntity().toCoupon())
                .price(getPrice())
                .discountPrice(getDiscountPrice())
                .useState(getUseState())
                .createdAt(getCreatedAt())
                .build();
    }
}
