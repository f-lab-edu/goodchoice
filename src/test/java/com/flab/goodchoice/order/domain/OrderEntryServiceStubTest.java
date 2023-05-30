package com.flab.goodchoice.order.domain;


import com.flab.goodchoice.item.domain.ItemCommand;
import com.flab.goodchoice.item.domain.ItemEntryService;
import com.flab.goodchoice.item.domain.ItemInfo;
import com.flab.goodchoice.item.domain.ItemOneService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderEntryServiceStubTest {

    @Test
    void 주문_생성(@Mock ItemEntryService itemEntryService,
               @Mock OrderEntryService orderEntryService,
               @Mock ItemOneService itemOneService) throws InterruptedException {

    }

}
