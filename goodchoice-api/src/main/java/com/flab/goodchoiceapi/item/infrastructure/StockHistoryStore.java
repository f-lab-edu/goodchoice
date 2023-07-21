package com.flab.goodchoiceapi.item.infrastructure;

import com.flab.goodchoiceitem.domain.StockHistory;

public interface StockHistoryStore {

    StockHistory store(StockHistory stockHistory);

}
