package com.flab.goodchoice.order.infrastructure;

import com.flab.goodchoice.order.domain.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
