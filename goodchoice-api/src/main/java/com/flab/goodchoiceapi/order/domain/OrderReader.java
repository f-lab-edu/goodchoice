package com.flab.goodchoiceapi.order.domain;

import com.flab.goodchoiceorder.domain.Order;

public interface OrderReader {

    Order getOrder(String orderToken);

}
