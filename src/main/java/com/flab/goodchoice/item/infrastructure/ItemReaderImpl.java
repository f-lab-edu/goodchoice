package com.flab.goodchoice.item.infrastructure;

import com.flab.goodchoice.item.domain.Item;
import com.flab.goodchoice.item.domain.ItemReader;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ItemReaderImpl implements ItemReader {

    private final ItemRepository itemRepository;

    @Override
    public Item getItemBy(String itemToken) {
        return itemRepository.findByItemToken(itemToken)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Item getItemByPessimisticLock(String itemToken) {
        return itemRepository.findByItemTokenWithPessimisticLock(itemToken)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public Item getItemByOptimisticLock(String itemToken) {
        return itemRepository.findByItemTokenWithPessimisticLock(itemToken)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<Item> getItems() {
        return itemRepository.findAll();
    }

}
