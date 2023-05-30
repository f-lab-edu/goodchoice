package com.flab.goodchoice.item.infrastructure;

import com.flab.goodchoice.item.domain.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StockRepository extends JpaRepository<Stock, Long> {
    Optional<Stock> findByItemToken(String itemToken);
}
