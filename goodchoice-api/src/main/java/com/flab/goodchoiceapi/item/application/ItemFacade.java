package com.flab.goodchoiceapi.item.application;


import com.flab.goodchoiceapi.item.domain.*;
import com.flab.goodchoiceitem.domain.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemFacade {

    private final ItemEntryService itemEntryService;

    private final ItemModifyService itemModifyService;

    private final ItemOneService itemOneService;

    private final ItemListService itemListService;

    public String registerItem (ItemCommand.RegisterItemRequest request) {
        var itemToken = itemEntryService.registerItem(request);
        return itemToken;
    }

    public ItemInfo.Main retrieveItemInfo(String itemToken) {
        return itemOneService.retrieveItemInfo(itemToken);
    }

    public void changeEndOfSaleItem(String itemToken) {
        itemModifyService.changeEndOfSale(itemToken);
    }

    public void updateItem(String itemToken, ItemCommand.UpdateItemRequest request) {
        itemModifyService.updateItem(itemToken, request);
    }

    public List<Item> retrieveAllItemInfo() {
        return itemListService.retrieveAllItemInfo();
    }
}
