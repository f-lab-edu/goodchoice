package com.flab.goodchoiceapi.order.domain;

import com.flab.goodchoiceitem.infrastructure.StockHistoryRepository;
import com.flab.goodchoiceitem.domain.StockHistory;
import com.flab.goodchoiceorder.domain.Order;
import com.flab.goodchoiceorder.domain.OrderStore;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEntryService {

    private final OrderStore orderStore;

    private final OrderItemSeriesFactory orderItemSeriesFactory;

    private final StockHistoryRepository stockHistoryRepository;

    @Transactional
    public String registerOrder(OrderCommand.RegisterOrder requestOrder) {
        Order order = orderStore.store(requestOrder.toEntity());
        orderItemSeriesFactory.store(order, requestOrder);

        StockHistory history = StockHistory.builder()
                .userId(requestOrder.getUserId())
                .orderToken(order.getOrderToken())
                .itemToken(requestOrder.getOrderItemList().get(0).getItemToken())
                .itemPrice(requestOrder.getOrderItemList().get(0).getItemPrice())
                .build();
        stockHistoryRepository.save(history);

        return order.getOrderToken();
    }

}
