package com.flab.goodchoiceapi.item.infrastructure;

import com.flab.goodchoiceitem.domain.StockHistory;
import com.flab.goodchoiceitem.infrastructure.StockHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
