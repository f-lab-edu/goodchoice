package com.flab.goodchoice.item.domain;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

    @Transactional
    public String registerItem(ItemCommand.RegisterItemRequest command) {
        var initItem = command.toEntity();
        var item = itemStore.store(initItem);
        return item.getItemToken();
    }

}
