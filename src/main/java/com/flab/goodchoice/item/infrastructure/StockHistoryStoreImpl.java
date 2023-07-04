package com.flab.goodchoice.item.infrastructure;

import com.flab.goodchoice.item.domain.StockHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockHistoryStoreImpl implements StockHistoryStore {

    private final StockHistoryRepository stockHistoryRepository;

    @Override
    public StockHistory store(StockHistory stockHistory) {
            return stockHistoryRepository.save(stockHistory);
    }
}
