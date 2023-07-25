package com.flab.goodchoicemember.domain.model;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Member {
    private Long id;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public Member(Long id) {
        this.id = id;
    }

    @Builder
    public Member(Long id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(id);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
