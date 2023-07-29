package com.flab.goodchoiceitem.infrastructure;

import com.flab.goodchoiceitem.domain.Item;
import com.flab.goodchoiceitem.domain.ItemStore;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemStoreImpl implements ItemStore {

    private final ItemRepository itemRepository;

    @Override
    public Item store(Item item) {
        return itemRepository.saveAndFlush(item);
    }

}
