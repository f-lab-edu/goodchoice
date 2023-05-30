package com.flab.goodchoice.order.application;


import com.flab.goodchoice.order.domain.OrderCommand;
import com.flab.goodchoice.order.domain.OrderEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderFacade {

    private final OrderEntryService orderEntryService;

    public String registerOrder(OrderCommand.RegisterOrder registerOrder) {
        var orderToken = orderEntryService.registerOrder(registerOrder);
        return orderToken;
    }

}
