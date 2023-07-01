package com.flab.goodchoice.order.application;

import com.flab.goodchoice.global.config.aop.DistributedLock;
import com.flab.goodchoice.item.domain.*;
import com.flab.goodchoice.order.domain.OrderCommand;
import com.flab.goodchoice.order.domain.OrderEntryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderFacade {
    private final OrderEntryService orderEntryService;

    private final ItemOneService itemOneService;

    private final RedisTemplate redisTemplate;

    @DistributedLock(key = "#registerOrder.getUserId()")
    public String registerOrder(OrderCommand.RegisterOrder registerOrder) throws Exception {
        ItemInfo.Main item = itemOneService.retrieveItemInfo(registerOrder.getOrderItemList().get(0).getItemToken());

        SetOperations<String, Object> setOperations = redisTemplate.opsForSet();
        String key = item.getItemToken() + "stocks:total";
        Long total = setOperations.size(key).longValue();
        if (total == item.getItemStock()) {
            throw new Exception("재고가 부족합니다.");
        }

        var orderToken = orderEntryService.registerOrder(registerOrder);

        setOperations.add(key, orderToken);
        return orderToken;
    }

}
