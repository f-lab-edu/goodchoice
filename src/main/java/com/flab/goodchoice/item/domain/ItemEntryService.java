package com.flab.goodchoice.item.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemEntryService {

    private final ItemStore itemStore;

    private final RedisTemplate redisTemplate;

    @Transactional
    public String registerItem(ItemCommand.RegisterItemRequest command) {
        var initItem = command.toEntity();
        var item = itemStore.store(initItem);

        // TODO :: redis에 상품 재고 저장
        var key = "stock:total";
//        final ValueOperations<String, String> stringStringValueOperations = redisTemplate.opsForValue();
//        stringStringValueOperations.set(key, initItem.getItemStock().toString());
        redisTemplate.opsForSet().add( initItem.getItemToken(), "10");

        return item.getItemToken();
    }

}
