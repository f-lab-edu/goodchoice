package com.flab.goodchoiceapi.order.domain;

import com.flab.goodchoiceorder.domain.Order;
import com.flab.goodchoiceorder.domain.OrderItem;

public interface OrderStore {

    Order store(Order order);

    OrderItem store(OrderItem orderItem);

}
