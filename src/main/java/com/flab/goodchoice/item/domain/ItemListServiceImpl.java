package com.flab.goodchoice.item.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemListServiceImpl implements ItemListService {

    private final ItemReader itemReader;

    @Override
    public List<Item> retrieveAllItemInfo() {
        return itemReader.getItems();
    }
}
