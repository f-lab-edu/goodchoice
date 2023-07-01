package com.flab.goodchoice.item.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Getter
@Entity
@NoArgsConstructor
public class StockHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    private String itemToken;

    private Long userId;

    private String orderToken;

    private Long itemPrice;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        PLUS("재고사용량 증가"),
        MINUS("재고사용량 감소");

        private final String description;
    }

    @Builder
    public StockHistory(String itemToken, Long userId, String orderToken, Long itemPrice) {
        this.itemToken = itemToken;
        this.userId = userId;
        this.orderToken = orderToken;
        this.itemPrice = itemPrice;
        this.status = Status.PLUS;
        this.createdAt = ZonedDateTime.now();
    }

}
