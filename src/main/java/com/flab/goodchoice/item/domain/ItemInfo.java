package com.flab.goodchoice.item.domain;

import lombok.Getter;
import lombok.ToString;

public class ItemInfo {

    @Getter
    @ToString
    public static class Main {
        private final String itemToken;
        private final String itemName;
        private final Long itemPrice;
        private final Long itemStock;
        private final Item.Status status;

        public Main(Item item) {
            this.itemToken = item.getItemToken();
            this.itemName = item.getItemName();
            this.itemPrice = item.getItemPrice();
            this.itemStock = item.getItemStock();
            this.status = item.getStatus();
        }
    }

}
