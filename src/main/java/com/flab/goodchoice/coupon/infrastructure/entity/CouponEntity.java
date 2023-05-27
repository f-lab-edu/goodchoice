package com.flab.goodchoice.coupon.infrastructure.entity;

import com.flab.goodchoice.coupon.domain.Coupon;
import com.flab.goodchoice.coupon.domain.CouponType;
import com.flab.goodchoice.coupon.domain.State;
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
@Table(name = "coupon")
public class CouponEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "coupon_token", columnDefinition = "BINARY(16)", nullable = false)
    private UUID couponToken;

    @Column(name = "coupon_name", nullable = false)
    private String couponName;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "coupon_type")
    private CouponType couponType;

    @Column(name = "discount_value")
    private int discountValue;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public CouponEntity(UUID couponToken, String couponName, int stock, CouponType couponType, int discountValue, State state) {
        this.couponToken = couponToken;
        this.couponName = couponName;
        this.stock = stock;
        this.couponType = couponType;
        this.discountValue = discountValue;
        this.state = state;
    }

    public CouponEntity(Long id, UUID couponToken, String couponName, int stock, CouponType couponType, int discountValue, State state) {
        this(couponToken, couponName, stock, couponType, discountValue, state);
        this.id = id;
    }

    @Builder
    public CouponEntity(Long id, UUID couponToken, String couponName, int stock, CouponType couponType, int discountValue, State state, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(id, couponToken, couponName, stock, couponType, discountValue, state);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Coupon toCoupon() {
        return Coupon.builder()
                .id(getId())
                .couponToken(getCouponToken())
                .couponName(getCouponName())
                .stock(getStock())
                .couponType(getCouponType())
                .discountValue(getDiscountValue())
                .state(getState())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    public static CouponEntity of(Coupon coupon) {
        return CouponEntity.builder()
                .id(coupon.getId())
                .couponToken(coupon.getCouponToken())
                .couponName(coupon.getCouponName())
                .stock(coupon.getStock())
                .couponType(coupon.getCouponType())
                .discountValue(coupon.getDiscountValue())
                .state(coupon.getState())
                .createdAt(coupon.getCreatedAt())
                .updatedAt(coupon.getUpdatedAt())
                .build();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof CouponEntity that)) return false;
        return Objects.equals(couponToken, that.couponToken);
    }

    @Override
    public int hashCode() {
        return Objects.hash(couponToken);
    }
}
