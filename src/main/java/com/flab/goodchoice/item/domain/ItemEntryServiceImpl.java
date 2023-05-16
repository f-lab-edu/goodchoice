package com.flab.goodchoice.item.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemEntryServiceImpl implements ItemEntryService {

    private final ItemStore itemStore;

    @Override
    @Transactional
    public String registerItem(ItemCommand.RegisterItemRequest command) {
        var initItem = command.toEntity();
        var item = itemStore.store(initItem);
        return item.getItemToken();
    }

}
