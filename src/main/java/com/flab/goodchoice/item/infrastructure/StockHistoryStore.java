package com.flab.goodchoice.item.infrastructure;

import com.flab.goodchoice.item.domain.StockHistory;

public interface StockHistoryStore {

    StockHistory store(StockHistory stockHistory);

}
