package com.flab.goodchoiceapi.item.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemModifyService {

    private final ItemReader itemReader;

    @Transactional
    public void changeEndOfSale(String itemToken) {
        var item = itemReader.getItemBy(itemToken);
        item.changeEndOfSale();
    }

    @Transactional
    public ItemInfo.Main updateItem(String itemToken, ItemCommand.UpdateItemRequest request) {
        var item = itemReader.getItemBy(itemToken);
        item.update(request.getItemName(), request.getItemPrice(), request.getItemStock());
        return new ItemInfo.Main(item);
    }

}
