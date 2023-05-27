package com.flab.goodchoice.coupon.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {
    private Long id;
    private UUID couponToken;
    private String couponName;
    private int stock;
    private CouponType couponType;
    private int discountValue;
    private State state;
    private LocalDateTime createdAt;
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

    @Builder
    public Coupon(Long id, UUID couponToken, String couponName, int stock, CouponType couponType, int discountValue, State state, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.couponToken = couponToken;
        this.couponName = couponName;
        this.stock = stock;
        this.couponType = couponType;
        this.discountValue = discountValue;
        this.state = state;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
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
