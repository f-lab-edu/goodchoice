package com.flab.goodchoice.item.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemStore itemStore;

    private final ItemReader itemReader;

    @Override
    @Transactional
    public String registerItem(ItemCommand.RegisterItemRequest command) {
        var initItem = command.toEntity();
        var item = itemStore.store(initItem);
        return item.getItemToken();
    }

    @Override
    @Transactional(readOnly = true)
    public ItemInfo.Main retrieveItemInfo(String itemToken) {
        var item = itemReader.getItemBy(itemToken);
        return new ItemInfo.Main(item);
    }

    @Override
    @Transactional
    public void changeEndOfSale(String itemToken) {
        var item = itemReader.getItemBy(itemToken);
        item.changeEndOfSale();
    }

    @Override
    @Transactional
    public ItemInfo.Main updateItem(String itemToken, ItemCommand.UpdateItemRequest request) {
        var item = itemReader.getItemBy(itemToken);
        item.update(request.getItemName(), request.getItemPrice(), request.getItemStock());
        return new ItemInfo.Main(item);
    }

    @Override
    public List<Item> retrieveAllItemInfo() {
        return itemReader.getItems();
    }
}
