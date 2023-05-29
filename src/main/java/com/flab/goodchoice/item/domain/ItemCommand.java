package com.flab.goodchoice.item.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class ItemCommand {

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class RegisterItemRequest {
        private final String itemName;
        private final Long itemPrice;
        private final Long itemStock;

        public Item toEntity() {
            return Item.builder()
                    .itemName(itemName)
                    .itemPrice(itemPrice)
                    .itemStock(itemStock)
                    .build();
        }

    }

    @Getter
    @Builder
    @ToString
    @AllArgsConstructor
    public static class UpdateItemRequest {
        private final String itemName;
        private final Long itemPrice;
        private final Long itemStock;

        public Item toEntity() {
            return Item.builder()
                    .itemName(itemName)
                    .itemPrice(itemPrice)
                    .itemStock(itemStock)
                    .build();
        }
    }
}
