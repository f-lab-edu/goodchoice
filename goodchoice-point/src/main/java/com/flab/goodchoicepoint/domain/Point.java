package com.flab.goodchoicepoint.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;

    private Long memberId;

    private Long itemId;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Setter
    @ColumnDefault("0")
    private String amount;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime expireAt;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        VALID("활성화"), INVALID("비화성화");

        private final String description;
    }

    @Builder
    public Point(Long memberId, Long itemId, String amount) {
        this.memberId = memberId;
        this.itemId = itemId;
        this.amount = amount;
        this.status = Status.VALID;
        this.expireAt = LocalDateTime.now().plusYears(1);
    }

    public Point setInactive() {
        status = Status.INVALID;
        return this;
    }

}
