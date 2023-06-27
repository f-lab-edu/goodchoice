package com.flab.goodchoice.order.domain;

import com.flab.goodchoice.item.domain.Item;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

public class OrderCommand {

    @Getter
    @Builder
    @ToString
    public static class RegisterOrder {

        private final Long userId;

        private final String payMethod;

        private final String receiverName;

        private final String receiverPhone;

        private final List<RegisterOrderItem> orderItemList;

        public Order toEntity() {
            return Order.builder()
                    .userId(userId)
                    .payMethod(payMethod)
                    .receiverName(receiverName)
                    .receiverPhone(receiverPhone)
                    .build();
        }
    }

    @Getter
    @Builder
    @ToString
    public static class RegisterOrderItem {

        private final Integer orderCount;

        private final String itemToken;

        private final String itemName;

        private final Long itemPrice;

        public OrderItem toEntity(Order order, Item item) {
            return OrderItem.builder()
                    .order(order)
                    .orderCount(orderCount)
                    .itemId(item.getId())
                    .itemToken(itemToken)
                    .itemName(itemName)
                    .itemPrice(itemPrice)
                    .build();
        }
    }
}
