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

    @Column(name = "coupon_token", nullable = false)
    private UUID couponToken;

    @Column(name = "coupon_name", nullable = false)
    private String couponName;

    @Column(name = "stock", nullable = false)
    private int stock;

    @Enumerated(EnumType.STRING)
    @Column(name = "state", nullable = false)
    private State state;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public Coupon(UUID couponToken, String couponName, int stock, State state) {
        Assert.hasText(couponName, "쿠폰명을 입력해주세요.");
        Assert.isTrue(stock >= 0, "쿠폰 갯수는 0보다 작을 수 없습니다.");

        this.couponToken = couponToken;
        this.couponName = couponName;
        this.stock = stock;
        this.state = state;
    }

    public void modify(String couponName, int stock) {
        if (StringUtils.hasText(couponName)) {
            this.couponName = couponName;
        }

        if (stock >= 0) {
            this.stock = stock;
        }
    }
}
