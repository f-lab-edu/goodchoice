package com.flab.goodchoiceitem.domain;

import com.flab.goodchoiceitem.common.util.TokenGenerator;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import org.apache.commons.lang3.StringUtils;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.security.InvalidParameterException;
import java.time.ZonedDateTime;

@Getter
@Entity
@NoArgsConstructor
@Table(name = "items")
public class Item {

    private static final String ITEM_PREFIX = "itm_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemToken;

    private String itemName;

    private Long itemPrice;

    private Long itemStock;

    @Enumerated(EnumType.STRING)
    private Status status;

    @CreationTimestamp
    private ZonedDateTime createdAt;

    @UpdateTimestamp
    private ZonedDateTime updatedAt;

    @UpdateTimestamp
    private ZonedDateTime deletedAt;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        PREPARE("판매준비중"),
        ON_SALE("판매중"),
        END_OF_SALE("판매종료");

        private final String description;
    }

    @Builder
    public Item(String itemName, Long itemPrice, Long itemStock) {
        if (StringUtils.isBlank(itemName)) throw new InvalidParameterException("Item.itemName");
        if (itemPrice == null) throw new InvalidParameterException("Item.itemPrice");
        if (itemStock == null) throw new InvalidParameterException("Item.itemStock");

        this.itemToken = TokenGenerator.randomCharacterWithPrefix(ITEM_PREFIX);
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
        this.status = Status.PREPARE;
    }

    public void update(String itemName, Long itemPrice, Long itemStock) {
        this.itemName = itemName;
        this.itemPrice = itemPrice;
        this.itemStock = itemStock;
    }

    public void changeEndOfSale() {
        this.status = Status.END_OF_SALE;
    }

    public void decrease(Long itemStock) {
        if (this.itemStock - itemStock < 0) {
            throw new RuntimeException("수량이 부족합니다.");
        }

        this.itemStock = this.itemStock - itemStock;
    }

}
