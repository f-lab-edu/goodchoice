package com.flab.goodchoice.member.infrastructure.entity;

import com.flab.goodchoice.member.domain.model.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name = "member")
public class MemberEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreatedDate
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    public MemberEntity(Long id) {
        this.id = id;
    }

    @Builder
    public MemberEntity(Long id, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this(id);
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public Member toMember() {
        return Member.builder()
                .id(getId())
                .createdAt(getCreatedAt())
                .updatedAt(getUpdatedAt())
                .build();
    }

    public static MemberEntity of(Member member) {
        return MemberEntity.builder()
                .id(member.getId())
                .createdAt(member.getCreatedAt())
                .updatedAt(member.getUpdatedAt())
                .build();
    }
}
