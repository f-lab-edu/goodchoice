package com.flab.goodchoiceorder.domain;

public interface OrderStore {

    Order store(Order order);

    OrderItem store(OrderItem orderItem);

}
