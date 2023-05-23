package com.flab.goodchoice.item.domain;

import java.util.List;

public interface ItemReader {
    Item getItemBy(String itemToken);

    List<Item> getItems();
}
