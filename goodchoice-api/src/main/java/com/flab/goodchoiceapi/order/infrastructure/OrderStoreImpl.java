package com.flab.goodchoiceapi.order.infrastructure;

import com.flab.goodchoiceorder.domain.Order;
import com.flab.goodchoiceorder.domain.OrderItem;
import com.flab.goodchoiceapi.order.domain.OrderStore;
import com.flab.goodchoiceorder.infrastructure.OrderItemRepository;
import com.flab.goodchoiceorder.infrastructure.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class OrderStoreImpl implements OrderStore {

    private final OrderRepository orderRepository;

    private final OrderItemRepository orderItemRepository;

    @Override
    public Order store(Order order) {
        return orderRepository.saveAndFlush(order);
    }

    @Override
    public OrderItem store(OrderItem orderItem) {
        return orderItemRepository.saveAndFlush(orderItem);
    }
}
