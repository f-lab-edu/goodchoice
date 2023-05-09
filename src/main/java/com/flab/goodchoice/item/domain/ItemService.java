package com.flab.goodchoice.item.domain;

import java.util.List;

public interface ItemService {
    String registerItem(ItemCommand.RegisterItemRequest request);

    ItemInfo.Main retrieveItemInfo(String itemToken);

    void changeEndOfSale(String itemToken);

    ItemInfo.Main updateItem(String itemToken, ItemCommand.UpdateItemRequest request);

    List<Item> retrieveAllItemInfo();
}
