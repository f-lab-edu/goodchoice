package com.flab.goodchoiceapi.item.interfaces;

import com.flab.goodchoiceitem.domain.Item;
import lombok.*;

public class ItemDto {

    @Getter
    @Setter
    @ToString
    public static class RegisterItemRequest {
        private String itemName;
        private Long itemPrice;
        private Long itemStock;
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterResponse {
        private final String itemToken;
    }

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final String itemToken;
        private final String itemName;
        private final Long itemPrice;
        private Long itemStock;
        private final Item.Status status;
    }

    @Getter
    @Setter
    @ToString
    public static class UpdateItemRequest {
        private String itemName;
        private Long itemPrice;
        private Long itemStock;
    }

    @Getter
    @Setter
    @ToString
    public static class ChangeStatusItemRequest {
        private String itemToken;
    }

}
