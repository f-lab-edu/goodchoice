package com.flab.goodchoicecoupon.infrastructure.entity;

import com.flab.goodchoiceapi.coupon.domain.CouponUseHistory;
import com.flab.goodchoiceapi.coupon.domain.UseState;
import com.flab.goodchoiceapi.member.infrastructure.entity.MemberEntity;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private MemberEntity member;

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

    public CouponUseHistoryEntity(MemberEntity member, CouponEntity couponEntity, int price, int discountPrice, UseState useState) {
        this.member = member;
        this.couponEntity = couponEntity;
        this.price = price;
        this.discountPrice = discountPrice;
        this.useState = useState;
    }

    @Builder
    public CouponUseHistoryEntity(Long id, MemberEntity member, CouponEntity couponEntity, int price, int discountPrice, UseState useState, LocalDateTime createdAt) {
        this.id = id;
        this.member = member;
        this.couponEntity = couponEntity;
        this.price = price;
        this.discountPrice = discountPrice;
        this.useState = useState;
        this.createdAt = createdAt;
    }

    public CouponUseHistory toCouponUseHistory() {
        return CouponUseHistory.builder()
                .id(getId())
                .member(getMember().toMember())
                .coupon(getCouponEntity().toCoupon())
                .price(getPrice())
                .discountPrice(getDiscountPrice())
                .useState(getUseState())
                .createdAt(getCreatedAt())
                .build();
    }
}
