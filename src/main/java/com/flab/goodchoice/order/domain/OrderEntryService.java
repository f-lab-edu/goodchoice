package com.flab.goodchoice.order.domain;

import com.flab.goodchoice.item.domain.Item;
import com.flab.goodchoice.item.domain.ItemReader;
import com.flab.goodchoice.item.domain.ItemStore;
import com.flab.goodchoice.item.domain.StockHistory;
import com.flab.goodchoice.item.infrastructure.LockRepository;
import com.flab.goodchoice.item.infrastructure.StockHistoryRepository;
import com.flab.goodchoice.item.infrastructure.StockRepository;
import com.flab.goodchoice.item.infrastructure.redis.RedisLockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderEntryService {

    private final OrderStore orderStore;

//    private final OrderReader orderReader;

    private final ItemReader itemReader;

    private final ItemStore itemStore;

    private final OrderItemSeriesFactory orderItemSeriesFactory;

    private final RedisLockRepository redisLockRepository;

    private final LockRepository lockRepository;

    private final StockRepository stockRepository;

    private final StockHistoryRepository stockHistoryRepository;

    private final RedissonClient redissonClient;

    private final RedisTemplate redisTemplate;

    @Transactional
    public String registerOrder(OrderCommand.RegisterOrder requestOrder) {
        Order order = orderStore.store(requestOrder.toEntity());
        orderItemSeriesFactory.store(order, requestOrder);
        return order.getOrderToken();
    }

    @Transactional
    public String registerOrderWithPessimisticLock(OrderCommand.RegisterOrder requestOrder) {
        Order order = orderStore.store(requestOrder.toEntity());
        orderItemSeriesFactory.storeWithPessimisticLock(order, requestOrder);
        return order.getOrderToken();
    }

    @Transactional
    public String registerOrderWithOptimisticLock(OrderCommand.RegisterOrder requestOrder) {
        Order order = orderStore.store(requestOrder.toEntity());
        orderItemSeriesFactory.storeWithOptimisticLock(order, requestOrder);
        return order.getOrderToken();
    }

    @Transactional
    public String registerOrderWithNamedLock(OrderCommand.RegisterOrder requestOrder) {
        Order order = orderStore.store(requestOrder.toEntity());
        List<OrderItem> orderItems = orderItemSeriesFactory.store(order, requestOrder);

        var item = itemReader.getItemBy(orderItems.get(0).getItemToken());
        try {
            lockRepository.getLock(item.getItemToken());
            item.decrease(1L);
            itemStore.store(item);

        } finally {
            lockRepository.releaseLock(item.getItemToken());
        }

        return order.getOrderToken();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String registerOrderWithLettuce(OrderCommand.RegisterOrder requestOrder) {
        Order order = orderStore.store(requestOrder.toEntity());
        List<OrderItem> orderItems = orderItemSeriesFactory.store(order, requestOrder);

        var item = itemReader.getItemBy(orderItems.get(0).getItemToken());
        while (!redisLockRepository.lock(item.getId())) {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            var stock = stockRepository.findById(item.getId()).orElseThrow();
            stock.decrease(1L);
            stockRepository.save(stock);
        } finally {
            redisLockRepository.unlock(item.getId());
        }

        StockHistory history = StockHistory.builder()
                .userId(1L)
                .orderToken(order.getOrderToken())
                .itemToken(item.getItemToken())
                .itemPrice(item.getItemPrice())
                .build();

        stockHistoryRepository.save(history);

        return order.getOrderToken();
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String registerOrderWithRedission(OrderCommand.RegisterOrder requestOrder) {
        Order order = orderStore.store(requestOrder.toEntity());
        List<OrderItem> orderItems = orderItemSeriesFactory.store(order, requestOrder);

        var item = itemReader.getItemBy(orderItems.get(0).getItemToken());

        StockHistory history = StockHistory.builder()
                .userId(1L)
                .orderToken(order.getOrderToken())
                .itemToken(item.getItemToken())
                .itemPrice(item.getItemPrice())
                .build();
        stockHistoryRepository.saveAndFlush(history);

        RLock lock = redissonClient.getLock(orderItems.get(0).getItemToken());

        try {

            boolean available = lock.tryLock(10, 1, TimeUnit.SECONDS);
            if (!available) {
                System.out.println("lock 획득 실패");
                return null;
            }

            // redis
            String stock = String.valueOf(redisTemplate.opsForSet().members(item.getItemToken()));
            redisTemplate.opsForSet().remove(item.getItemToken());
            int num = (Integer.parseInt(stock) - 1);
            redisTemplate.opsForSet().add(item.getItemToken(), String.valueOf(num));

            // mysql
//            var stock = stockRepository.findById(1L).orElseThrow();
//            stock.decrease(1L);
//            stockRepository.saveAndFlush(stock);

        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } finally {
            lock.unlock();
        }

        return order.getOrderToken();
    }

}
