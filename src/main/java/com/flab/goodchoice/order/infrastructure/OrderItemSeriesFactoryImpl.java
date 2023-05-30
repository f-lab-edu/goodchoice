package com.flab.goodchoice.order.infrastructure;

import com.flab.goodchoice.item.domain.ItemReader;
import com.flab.goodchoice.item.domain.ItemStore;
import com.flab.goodchoice.order.domain.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderItemSeriesFactoryImpl implements OrderItemSeriesFactory {

    private final ItemReader itemReader;

    private final ItemStore itemStore;

    private final OrderStore orderStore;

    @Override
    public List<OrderItem> store(Order order, OrderCommand.RegisterOrder requestOrder) {
        return requestOrder.getOrderItemList().stream()
                .map(orderItemRequest -> {
                    var item = itemReader.getItemBy(orderItemRequest.getItemToken());
                    item.decrease(1L);
                    itemStore.store(item);

                    var initOrderItem = orderItemRequest.toEntity(order, item);
                    var orderItem = orderStore.store(initOrderItem);
                    return orderItem;
                }).collect(Collectors.toList());
    }

    @Override
    public List<OrderItem> storeWithPessimisticLock(Order order, OrderCommand.RegisterOrder requestOrder) {
        return requestOrder.getOrderItemList().stream()
                .map(orderItemRequest -> {
                    var item = itemReader.getItemByPessimisticLock(orderItemRequest.getItemToken());
                    item.decrease(1L);
                    itemStore.store(item);

                    var initOrderItem = orderItemRequest.toEntity(order, item);
                    var orderItem = orderStore.store(initOrderItem);
                    return orderItem;
                }).collect(Collectors.toList());
    }

    @Override
    public List<OrderItem> storeWithOptimisticLock(Order order, OrderCommand.RegisterOrder requestOrder) {
        return requestOrder.getOrderItemList().stream()
                .map(orderItemRequest -> {
                    var item = itemReader.getItemByOptimisticLock(orderItemRequest.getItemToken());
                    item.decrease(1L);
                    itemStore.store(item);

                    var initOrderItem = orderItemRequest.toEntity(order, item);
                    var orderItem = orderStore.store(initOrderItem);
                    return orderItem;
                }).collect(Collectors.toList());
    }

}
