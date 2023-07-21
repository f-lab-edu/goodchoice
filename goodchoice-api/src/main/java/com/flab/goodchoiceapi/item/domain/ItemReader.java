package com.flab.goodchoiceapi.item.domain;

import com.flab.goodchoiceitem.domain.Item;

import java.util.List;

public interface ItemReader {
    Item getItemBy(String itemToken);

    Item getItemByPessimisticLock(String itemToken);

    Item getItemByOptimisticLock(String itemToken);

    List<Item> getItems();
}
