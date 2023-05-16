package com.flab.goodchoice.item.domain;

public interface ItemModifyService {

    void changeEndOfSale(String itemToken);

    ItemInfo.Main updateItem(String itemToken, ItemCommand.UpdateItemRequest request);

}
