package com.flab.goodchoice.order.domain;

import java.util.List;

public interface OrderItemSeriesFactory {
    List<OrderItem> store(Order order, OrderCommand.RegisterOrder requestOrder);

    List<OrderItem> storeWithPessimisticLock(Order order, OrderCommand.RegisterOrder requestOrder);

    List<OrderItem> storeWithOptimisticLock(Order order, OrderCommand.RegisterOrder requestOrder);
}
