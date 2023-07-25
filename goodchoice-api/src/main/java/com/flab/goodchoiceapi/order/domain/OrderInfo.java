package com.flab.goodchoiceapi.order.domain;

import com.flab.goodchoiceorder.domain.OrderItem;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.time.ZonedDateTime;
import java.util.List;

public class OrderInfo {

    @Getter
    @Builder
    @ToString
    public static class Main {
        private final Long orderId;
        private final String orderToken;
        private final Long userId;
        private final String payMethod;
        private final String receiverName;
        private final String receiverPhone;
        private final Long totalAmount;
        private final ZonedDateTime orderedAt;
        private final String status;
        private final String statusDescription;
        private final List<OrderItem> orderItemList;
    }

}
