package com.flab.goodchoiceapi.order.application;

import com.flab.goodchoiceapi.order.domain.OrderCommand;
import com.flab.goodchoiceapi.order.domain.OrderEntryService;
import com.flab.goodchoiceapi.item.domain.ItemInfo;
import com.flab.goodchoiceapi.item.domain.ItemOneService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderFacade {
    private final OrderEntryService orderEntryService;

    private final ItemOneService itemOneService;

    public String registerOrder(OrderCommand.RegisterOrder registerOrder) throws Exception {
        ItemInfo.Main item = itemOneService.retrieveItemInfo(registerOrder.getOrderItemList().get(0).getItemToken());

        var orderToken = orderEntryService.registerOrder(registerOrder);

        return orderToken;
    }

}
