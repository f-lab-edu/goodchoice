package com.flab.goodchoiceorder.domain;

import com.flab.goodchoiceorder.common.util.TokenGenerator;
import com.google.common.collect.Lists;
import lombok.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import javax.persistence.*;
import java.security.InvalidParameterException;
import java.time.ZonedDateTime;
import java.util.List;

@Slf4j
@Getter
@Entity
@ToString
@NoArgsConstructor
@Table(name = "orders")
public class Order {

    String ORDER_PREFIX = "ord_";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderToken;

    private Long userId;

    private String payMethod;

    private String receiverName;

    private String receiverPhone;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order", cascade = CascadeType.PERSIST)
    private List<OrderItem> orderItemList = Lists.newArrayList();

    private ZonedDateTime orderedAt;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Getter
    @RequiredArgsConstructor
    public enum Status {
        INIT("주문시작"),
        ORDER_COMPLETE("주문완료");

        private final String description;
    }

    @Builder
    public Order(
            Long userId,
            String payMethod,
            String receiverName,
            String receiverPhone
    ) {
        if (userId == null) throw new InvalidParameterException("Order.userId");
        if (StringUtils.isEmpty(payMethod)) throw new InvalidParameterException("Order.payMethod");
        if (StringUtils.isEmpty(receiverName)) throw new InvalidParameterException("Order.receiverName");
        if (StringUtils.isEmpty(receiverPhone)) throw new InvalidParameterException("Order.receiverPhone");

        this.orderToken = TokenGenerator.randomCharacterWithPrefix(ORDER_PREFIX);
        this.userId = userId;
        this.payMethod = payMethod;
        this.receiverName = receiverName;
        this.receiverPhone = receiverPhone;
        this.orderedAt = ZonedDateTime.now();
        this.status = Status.INIT;
    }

    public void orderComplete() {
        if (this.status != Status.INIT) throw new IllegalStateException();
        this.status = Status.ORDER_COMPLETE;
    }

}
