package com.flab.goodchoiceitem.infrastructure;

import com.flab.goodchoiceitem.domain.StockHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockHistoryRepository extends JpaRepository<StockHistory, Long> {
}
