package com.flab.goodchoice.item.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemListService {

    private final ItemReader itemReader;

    public List<Item> retrieveAllItemInfo() {
        return itemReader.getItems();
    }
}
