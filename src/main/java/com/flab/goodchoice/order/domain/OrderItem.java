package com.flab.goodchoice.order.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import javax.persistence.*;
import java.security.InvalidParameterException;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "order_items")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private Integer orderCount;

    private Long itemId;

    private String itemName;

    private String itemToken;

    private Long itemPrice;

    @Builder
    public OrderItem(
            Order order,
            Integer orderCount,
            Long itemId,
            String itemName,
            String itemToken,
            Long itemPrice
    ) {

        if (order == null) throw new InvalidParameterException("OrderItemLine.order");
        if (orderCount == null) throw new InvalidParameterException("OrderItemLine.orderCount");
        if (itemId == null && StringUtils.isEmpty(itemName))
            throw new InvalidParameterException("OrderItemLine.itemNo and itemName");
        if (StringUtils.isEmpty(itemToken)) throw new InvalidParameterException("OrderItemLine.itemToken");
        if (itemPrice == null) throw new InvalidParameterException("OrderItemLine.itemPrice");

        this.order = order;
        this.orderCount = orderCount;
        this.itemId = itemId;
        this.itemName = itemName;
        this.itemToken = itemToken;
        this.itemPrice = itemPrice;
    }

}
