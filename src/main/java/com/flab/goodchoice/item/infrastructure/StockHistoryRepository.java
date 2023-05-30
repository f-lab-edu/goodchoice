package com.flab.goodchoice.item.infrastructure;

import com.flab.goodchoice.item.domain.Item;
import com.flab.goodchoice.item.domain.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
}
