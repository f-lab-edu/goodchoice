package com.flab.goodchoice.order.domain;

public interface OrderReader {

    Order getOrder(String orderToken);

}
