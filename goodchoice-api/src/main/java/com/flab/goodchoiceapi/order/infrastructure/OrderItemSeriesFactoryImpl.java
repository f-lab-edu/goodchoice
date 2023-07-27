package com.flab.goodchoiceapi.order.infrastructure;

import com.flab.goodchoiceapi.order.domain.*;
import com.flab.goodchoiceitem.domain.ItemReader;
import com.flab.goodchoiceorder.domain.Order;
import com.flab.goodchoiceorder.domain.OrderItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemSeriesFactoryImpl implements OrderItemSeriesFactory {

    private final ItemReader itemReader;

    private final OrderStore orderStore;

    @Override
    public List<OrderItem> store(Order order, OrderCommand.RegisterOrder requestOrder) {
        return requestOrder.getOrderItemList().stream()
                .map(orderItemRequest -> {
                    var item = itemReader.getItemBy(orderItemRequest.getItemToken());
                    var initOrderItem = orderItemRequest.toEntity(order, item);
                    var orderItem = orderStore.store(initOrderItem);
                    return orderItem;
                }).collect(Collectors.toList());
    }

}
