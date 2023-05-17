package com.flab.goodchoice.coupon.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "coupon")
public class Coupon {

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

    public Coupon(UUID couponToken, String couponName, int stock, CouponType couponType, int discountValue, State state) {
        Assert.hasText(couponName, "쿠폰명을 입력해주세요.");
        Assert.isTrue(stock >= 0, "쿠폰 갯수는 0보다 작을 수 없습니다.");
        Assert.notNull(couponType, "쿠폰 할인 종류를 입력해주세요.");
        Assert.isTrue(discountValue > 0, "쿠폰 할인 값은 0보다 작을 수 없습니다.");

        this.couponToken = couponToken;
        this.couponName = couponName;
        this.stock = stock;
        this.couponType = couponType;
        this.discountValue = discountValue;
        this.state = state;
    }

    public void modify(String couponName, int stock) {
        if (!StringUtils.hasText(couponName)) {
            throw new IllegalArgumentException("빈 쿠폰명으로 수정할 수 없습니다.");
        }
        if (stock < 0) {
            throw new IllegalArgumentException("쿠폰 갯수는 0보다 작을 수 없습니다.");
        }

        this.couponName = couponName;
        this.stock = stock;
    }

    public void usedCoupon() {
        if (this.stock <= 0) {
            throw new IllegalArgumentException("선택된 쿠폰이 모두 소진 되었습니다.");
        }
        this.stock--;
    }

    public void usedCancelCoupon() {
        this.stock++;
    }

    public void delete() {
        this.state = State.INACTIVITY;
    }
}
