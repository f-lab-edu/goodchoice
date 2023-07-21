package com.flab.goodchoiceapi.order.domain;

import com.flab.goodchoiceorder.domain.Order;
import com.flab.goodchoiceorder.domain.OrderItem;

import java.util.List;

public interface OrderItemSeriesFactory {
    List<OrderItem> store(Order order, OrderCommand.RegisterOrder requestOrder);

}
