package com.flab.goodchoice.order.domain;

import java.util.List;

public interface OrderItemSeriesFactory {
    List<OrderItem> store(Order order, OrderCommand.RegisterOrder requestOrder);

}
