package com.flab.goodchoiceorder.infrastructure;

import com.flab.goodchoiceorder.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByOrderToken(String orderToken);
}
