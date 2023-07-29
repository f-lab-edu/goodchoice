package com.flab.goodchoiceitem.domain;

import java.util.List;

public interface ItemReader {
    Item getItemBy(String itemToken);

    Item getItemByPessimisticLock(String itemToken);

    Item getItemByOptimisticLock(String itemToken);

    List<Item> getItems();
}
