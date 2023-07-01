package com.flab.goodchoice.order.domain;

public interface OrderStore {

    Order store(Order order);

    OrderItem store(OrderItem orderItem);

}
