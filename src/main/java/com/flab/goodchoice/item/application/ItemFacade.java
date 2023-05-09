package com.flab.goodchoice.item.application;


import com.flab.goodchoice.item.domain.Item;
import com.flab.goodchoice.item.domain.ItemCommand;
import com.flab.goodchoice.item.domain.ItemInfo;
import com.flab.goodchoice.item.domain.ItemService;
import com.flab.goodchoice.item.interfaces.ItemDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemFacade {

    private final ItemService itemService;

    public String registerItem (ItemCommand.RegisterItemRequest request) {
        var itemToken = itemService.registerItem(request);
        return itemToken;
    }

    public ItemInfo.Main retrieveItemInfo(String itemToken) {
        return itemService.retrieveItemInfo(itemToken);
    }

    public void changeEndOfSaleItem(String itemToken) {
        itemService.changeEndOfSale(itemToken);
    }

    public void updateItem(String itemToken, ItemCommand.UpdateItemRequest request) {
        itemService.updateItem(itemToken, request);
    }

    public List<Item> retrieveAllItemInfo() {
        return itemService.retrieveAllItemInfo();
    }
}
