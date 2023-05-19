package com.flab.goodchoice.item.domain;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ItemOneService {

    private final ItemReader itemReader;

    @Transactional(readOnly = true)
    public ItemInfo.Main retrieveItemInfo(String itemToken) {
        var item = itemReader.getItemBy(itemToken);
        return new ItemInfo.Main(item);
    }

}
