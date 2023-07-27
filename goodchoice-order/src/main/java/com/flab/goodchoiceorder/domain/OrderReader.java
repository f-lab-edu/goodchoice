package com.flab.goodchoiceorder.domain;

public interface OrderReader {

    Order getOrder(String orderToken);

}
