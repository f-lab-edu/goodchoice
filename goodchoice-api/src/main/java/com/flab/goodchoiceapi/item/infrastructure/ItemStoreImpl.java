package com.flab.goodchoiceapi.item.infrastructure;

import com.flab.goodchoiceitem.domain.Item;
import com.flab.goodchoiceapi.item.domain.ItemStore;
import com.flab.goodchoiceitem.infrastructure.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemStoreImpl implements ItemStore {

    private final ItemRepository itemRepository;

    @Override
    public Item store(Item item) {
        validCheck(item);
        return itemRepository.saveAndFlush(item);
    }

    private void validCheck(Item item) {
        if (StringUtils.isEmpty(item.getItemToken())) throw new InvalidParameterException("Item.itemToken");
        if (StringUtils.isEmpty(item.getItemName())) throw new InvalidParameterException("Item.itemName");
        if (item.getItemPrice() == null) throw new InvalidParameterException("Item.itemPrice");
        if (item.getItemStock() == null) throw new InvalidParameterException("Item.itemStock");
        if (item.getStatus() == null) throw new InvalidParameterException("Item.status");
    }

}
