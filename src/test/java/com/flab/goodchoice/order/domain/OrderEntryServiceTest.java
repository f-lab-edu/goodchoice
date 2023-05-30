package com.flab.goodchoice.order.domain;

import com.flab.goodchoice.item.domain.*;
import com.flab.goodchoice.item.infrastructure.ItemRepository;
import com.flab.goodchoice.item.infrastructure.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class OrderEntryServiceTest {

    ItemCommand.RegisterItemRequest 판매준비중_단계의_상품_생성 = new ItemCommand.RegisterItemRequest("제주해비치", 100000L, 10L);

    @Autowired
    private OrderEntryService orderEntryService;

    @Autowired
    private ItemEntryService itemEntryService;

    @Autowired
    private ItemOneService itemOneService;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private EntityManager em;

//    private Stock stock;
//
//    private String itemToken;

    @Autowired
    RedisTemplate redisTemplate;

    @BeforeEach
    void setUp() {

    }

    @AfterEach
    public void delete() {
        itemRepository.deleteAll();
//        stockRepository.deleteAll();
    }

    @Test
    void 주문_생성() throws InterruptedException {
        //given
        String itemToken = itemEntryService.registerItem(판매준비중_단계의_상품_생성);

        // When
        OrderCommand.RegisterOrderItem 주문한_상품_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_리스트 = new ArrayList<>();
        주문한_상품_리스트.add(주문한_상품_생성);

        OrderCommand.RegisterOrder 주문_생성 = new OrderCommand.RegisterOrder(1L, "카드", "심은지","010-1234-0000", 주문한_상품_리스트);

        String orderToken = orderEntryService.registerOrder(주문_생성);

        // then
        ItemInfo.Main itemInfo = itemOneService.retrieveItemInfo(itemToken);
        assertEquals(9L, itemInfo.getItemStock());
    }

    @Test
    void 동시에_10개의_주문_생성_by_count_down_latch() throws InterruptedException {
        //given
        final int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // When
        String itemToken = itemEntryService.registerItem(판매준비중_단계의_상품_생성);

        OrderCommand.RegisterOrderItem 주문한_상품_1_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_2_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_3_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_4_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_5_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_6_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_7_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_8_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_9_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_10_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_1_리스트 = new ArrayList<>();
        주문한_상품_1_리스트.add(주문한_상품_1_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_2_리스트 = new ArrayList<>();
        주문한_상품_2_리스트.add(주문한_상품_2_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_3_리스트 = new ArrayList<>();
        주문한_상품_3_리스트.add(주문한_상품_3_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_4_리스트 = new ArrayList<>();
        주문한_상품_4_리스트.add(주문한_상품_4_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_5_리스트 = new ArrayList<>();
        주문한_상품_5_리스트.add(주문한_상품_5_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_6_리스트 = new ArrayList<>();
        주문한_상품_6_리스트.add(주문한_상품_6_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_7_리스트 = new ArrayList<>();
        주문한_상품_7_리스트.add(주문한_상품_7_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_8_리스트 = new ArrayList<>();
        주문한_상품_8_리스트.add(주문한_상품_8_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_9_리스트 = new ArrayList<>();
        주문한_상품_9_리스트.add(주문한_상품_9_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_10_리스트 = new ArrayList<>();
        주문한_상품_10_리스트.add(주문한_상품_10_생성);

        OrderCommand.RegisterOrder 주문_생성_1 = new OrderCommand.RegisterOrder(1L, "카드", "심은지", "010-0000-0001", 주문한_상품_1_리스트);
        OrderCommand.RegisterOrder 주문_생성_2 = new OrderCommand.RegisterOrder(2L, "카드", "가은지", "010-0000-0002", 주문한_상품_2_리스트);
        OrderCommand.RegisterOrder 주문_생성_3 = new OrderCommand.RegisterOrder(3L, "카드",  "나은지", "010-0000-0003", 주문한_상품_3_리스트);
        OrderCommand.RegisterOrder 주문_생성_4 = new OrderCommand.RegisterOrder(4L, "카드",  "다은지", "010-0000-0004", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_5 = new OrderCommand.RegisterOrder(5L, "카드",  "라은지", "010-0000-0005", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_6 = new OrderCommand.RegisterOrder(6L, "카드",  "마은지", "010-0000-0006", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_7 = new OrderCommand.RegisterOrder(7L, "카드",  "바은지", "010-0000-0007", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_8 = new OrderCommand.RegisterOrder(8L, "카드",  "사은지", "010-0000-0008", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_9 = new OrderCommand.RegisterOrder(9L, "카드",  "아은지", "010-0000-0009", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_10 = new OrderCommand.RegisterOrder(10L, "카드",  "자은지", "010-0000-00010", 주문한_상품_10_리스트);

        List<OrderCommand.RegisterOrder> orderLists = new ArrayList<>();
        orderLists.add(주문_생성_1);
        orderLists.add(주문_생성_2);
        orderLists.add(주문_생성_3);
        orderLists.add(주문_생성_4);
        orderLists.add(주문_생성_5);
        orderLists.add(주문_생성_6);
        orderLists.add(주문_생성_7);
        orderLists.add(주문_생성_8);
        orderLists.add(주문_생성_9);
        orderLists.add(주문_생성_10);

        for (int i = 0; i < threadCount; i++) {
            System.out.println("Thread " + Thread.currentThread().getId() + " is doing its work...");

            int n = i;
            executorService.submit(() -> {
                String orderToken = orderEntryService.registerOrder(orderLists.get(n));
                latch.countDown();
            });

            System.out.println("Thread " + Thread.currentThread().getId() + " has finished its work");
        }

        latch.await();
        Thread.sleep(10000);

        em.flush();
        em.clear();

        // then
        ItemInfo.Main itemInfo = itemOneService.retrieveItemInfo(itemToken);
        assertEquals(0L, itemInfo.getItemStock());
    }

    @Test
    void 동시에_10개의_주문_생성_by_cyclic_barrie() throws InterruptedException, BrokenBarrierException {
        //given
        final int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CyclicBarrier barrier = new CyclicBarrier(threadCount);

        // When
        String itemToken = itemEntryService.registerItem(판매준비중_단계의_상품_생성);

        OrderCommand.RegisterOrderItem 주문한_상품_1_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_2_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_3_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_4_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_5_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_6_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_7_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_8_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_9_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_10_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_1_리스트 = new ArrayList<>();
        주문한_상품_1_리스트.add(주문한_상품_1_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_2_리스트 = new ArrayList<>();
        주문한_상품_2_리스트.add(주문한_상품_2_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_3_리스트 = new ArrayList<>();
        주문한_상품_3_리스트.add(주문한_상품_3_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_4_리스트 = new ArrayList<>();
        주문한_상품_4_리스트.add(주문한_상품_4_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_5_리스트 = new ArrayList<>();
        주문한_상품_5_리스트.add(주문한_상품_5_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_6_리스트 = new ArrayList<>();
        주문한_상품_6_리스트.add(주문한_상품_6_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_7_리스트 = new ArrayList<>();
        주문한_상품_7_리스트.add(주문한_상품_7_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_8_리스트 = new ArrayList<>();
        주문한_상품_8_리스트.add(주문한_상품_8_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_9_리스트 = new ArrayList<>();
        주문한_상품_9_리스트.add(주문한_상품_9_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_10_리스트 = new ArrayList<>();
        주문한_상품_10_리스트.add(주문한_상품_10_생성);

        OrderCommand.RegisterOrder 주문_생성_1 = new OrderCommand.RegisterOrder(1L, "카드", "심은지", "010-0000-0001", 주문한_상품_1_리스트);
        OrderCommand.RegisterOrder 주문_생성_2 = new OrderCommand.RegisterOrder(2L, "카드", "가은지", "010-0000-0002", 주문한_상품_2_리스트);
        OrderCommand.RegisterOrder 주문_생성_3 = new OrderCommand.RegisterOrder(3L, "카드",  "나은지", "010-0000-0003", 주문한_상품_3_리스트);
        OrderCommand.RegisterOrder 주문_생성_4 = new OrderCommand.RegisterOrder(4L, "카드",  "다은지", "010-0000-0004", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_5 = new OrderCommand.RegisterOrder(5L, "카드",  "라은지", "010-0000-0005", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_6 = new OrderCommand.RegisterOrder(6L, "카드",  "마은지", "010-0000-0006", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_7 = new OrderCommand.RegisterOrder(7L, "카드",  "바은지", "010-0000-0007", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_8 = new OrderCommand.RegisterOrder(8L, "카드",  "사은지", "010-0000-0008", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_9 = new OrderCommand.RegisterOrder(9L, "카드",  "아은지", "010-0000-0009", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_10 = new OrderCommand.RegisterOrder(10L, "카드",  "자은지", "010-0000-00010", 주문한_상품_10_리스트);

        List<OrderCommand.RegisterOrder> orderLists = new ArrayList<>();
        orderLists.add(주문_생성_1);
        orderLists.add(주문_생성_2);
        orderLists.add(주문_생성_3);
        orderLists.add(주문_생성_4);
        orderLists.add(주문_생성_5);
        orderLists.add(주문_생성_6);
        orderLists.add(주문_생성_7);
        orderLists.add(주문_생성_8);
        orderLists.add(주문_생성_9);
        orderLists.add(주문_생성_10);

        for (int i = 0; i < threadCount; i++) {
            System.out.println("Thread " + Thread.currentThread().getId() + " is doing its work...");

            int n = i;
            executorService.submit(() -> {
                try {
                    barrier.await();
                    String orderToken = orderEntryService.registerOrder(orderLists.get(n));
                } catch (BrokenBarrierException | InterruptedException e) {
                    throw new RuntimeException(e);
                }
            });

            System.out.println("Thread " + Thread.currentThread().getId() + " has finished its work");
        }

        em.flush();
        em.clear();

        System.out.println("barrier" + barrier.getNumberWaiting());

        // then
        ItemInfo.Main itemInfo = itemOneService.retrieveItemInfo(itemToken);
        assertEquals(0L, itemInfo.getItemStock());
    }

    @Test
    void 동시에_10개의_주문_생성_with_pessimistic_lock() throws InterruptedException {
        //given
        final int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // When
        String itemToken = itemEntryService.registerItem(판매준비중_단계의_상품_생성);

        OrderCommand.RegisterOrderItem 주문한_상품_1_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_2_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_3_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_4_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_5_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_6_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_7_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_8_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_9_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_10_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_1_리스트 = new ArrayList<>();
        주문한_상품_1_리스트.add(주문한_상품_1_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_2_리스트 = new ArrayList<>();
        주문한_상품_2_리스트.add(주문한_상품_2_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_3_리스트 = new ArrayList<>();
        주문한_상품_3_리스트.add(주문한_상품_3_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_4_리스트 = new ArrayList<>();
        주문한_상품_4_리스트.add(주문한_상품_4_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_5_리스트 = new ArrayList<>();
        주문한_상품_5_리스트.add(주문한_상품_5_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_6_리스트 = new ArrayList<>();
        주문한_상품_6_리스트.add(주문한_상품_6_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_7_리스트 = new ArrayList<>();
        주문한_상품_7_리스트.add(주문한_상품_7_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_8_리스트 = new ArrayList<>();
        주문한_상품_8_리스트.add(주문한_상품_8_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_9_리스트 = new ArrayList<>();
        주문한_상품_9_리스트.add(주문한_상품_9_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_10_리스트 = new ArrayList<>();
        주문한_상품_10_리스트.add(주문한_상품_10_생성);

        OrderCommand.RegisterOrder 주문_생성_1 = new OrderCommand.RegisterOrder(1L, "카드", "심은지", "010-0000-0001", 주문한_상품_1_리스트);
        OrderCommand.RegisterOrder 주문_생성_2 = new OrderCommand.RegisterOrder(2L, "카드", "가은지", "010-0000-0002", 주문한_상품_2_리스트);
        OrderCommand.RegisterOrder 주문_생성_3 = new OrderCommand.RegisterOrder(3L, "카드",  "나은지", "010-0000-0003", 주문한_상품_3_리스트);
        OrderCommand.RegisterOrder 주문_생성_4 = new OrderCommand.RegisterOrder(4L, "카드",  "다은지", "010-0000-0004", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_5 = new OrderCommand.RegisterOrder(5L, "카드",  "라은지", "010-0000-0005", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_6 = new OrderCommand.RegisterOrder(6L, "카드",  "마은지", "010-0000-0006", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_7 = new OrderCommand.RegisterOrder(7L, "카드",  "바은지", "010-0000-0007", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_8 = new OrderCommand.RegisterOrder(8L, "카드",  "사은지", "010-0000-0008", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_9 = new OrderCommand.RegisterOrder(9L, "카드",  "아은지", "010-0000-0009", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_10 = new OrderCommand.RegisterOrder(10L, "카드",  "자은지", "010-0000-00010", 주문한_상품_10_리스트);

        List<OrderCommand.RegisterOrder> orderLists = new ArrayList<>();
        orderLists.add(주문_생성_1);
        orderLists.add(주문_생성_2);
        orderLists.add(주문_생성_3);
        orderLists.add(주문_생성_4);
        orderLists.add(주문_생성_5);
        orderLists.add(주문_생성_6);
        orderLists.add(주문_생성_7);
        orderLists.add(주문_생성_8);
        orderLists.add(주문_생성_9);
        orderLists.add(주문_생성_10);

        for (int i = 0; i < threadCount; i++) {
            System.out.println("Thread " + Thread.currentThread().getId() + " is doing its work...");

            int n = i;
            executorService.submit(() -> {
                String orderToken = orderEntryService.registerOrderWithPessimisticLock(orderLists.get(n));
                latch.countDown();
            });

            System.out.println("Thread " + Thread.currentThread().getId() + " has finished its work");
        }

        latch.await();
        Thread.sleep(10000);

        em.flush();
        em.clear();

        // then
        ItemInfo.Main itemInfo = itemOneService.retrieveItemInfo(itemToken);
        assertEquals(0L, itemInfo.getItemStock());
    }

    @Test
    @DisplayName("Optimistic Lock을 사용한다.")
    void 동시에_10개의_주문_생성_테스트() throws InterruptedException {
        //given
        final int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // When
        String itemToken = itemEntryService.registerItem(판매준비중_단계의_상품_생성);

        OrderCommand.RegisterOrderItem 주문한_상품_1_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_2_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_3_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_4_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_5_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_6_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_7_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_8_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_9_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_10_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_1_리스트 = new ArrayList<>();
        주문한_상품_1_리스트.add(주문한_상품_1_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_2_리스트 = new ArrayList<>();
        주문한_상품_2_리스트.add(주문한_상품_2_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_3_리스트 = new ArrayList<>();
        주문한_상품_3_리스트.add(주문한_상품_3_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_4_리스트 = new ArrayList<>();
        주문한_상품_4_리스트.add(주문한_상품_4_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_5_리스트 = new ArrayList<>();
        주문한_상품_5_리스트.add(주문한_상품_5_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_6_리스트 = new ArrayList<>();
        주문한_상품_6_리스트.add(주문한_상품_6_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_7_리스트 = new ArrayList<>();
        주문한_상품_7_리스트.add(주문한_상품_7_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_8_리스트 = new ArrayList<>();
        주문한_상품_8_리스트.add(주문한_상품_8_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_9_리스트 = new ArrayList<>();
        주문한_상품_9_리스트.add(주문한_상품_9_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_10_리스트 = new ArrayList<>();
        주문한_상품_10_리스트.add(주문한_상품_10_생성);

        OrderCommand.RegisterOrder 주문_생성_1 = new OrderCommand.RegisterOrder(1L, "카드", "심은지", "010-0000-0001", 주문한_상품_1_리스트);
        OrderCommand.RegisterOrder 주문_생성_2 = new OrderCommand.RegisterOrder(2L, "카드", "가은지", "010-0000-0002", 주문한_상품_2_리스트);
        OrderCommand.RegisterOrder 주문_생성_3 = new OrderCommand.RegisterOrder(3L, "카드",  "나은지", "010-0000-0003", 주문한_상품_3_리스트);
        OrderCommand.RegisterOrder 주문_생성_4 = new OrderCommand.RegisterOrder(4L, "카드",  "다은지", "010-0000-0004", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_5 = new OrderCommand.RegisterOrder(5L, "카드",  "라은지", "010-0000-0005", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_6 = new OrderCommand.RegisterOrder(6L, "카드",  "마은지", "010-0000-0006", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_7 = new OrderCommand.RegisterOrder(7L, "카드",  "바은지", "010-0000-0007", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_8 = new OrderCommand.RegisterOrder(8L, "카드",  "사은지", "010-0000-0008", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_9 = new OrderCommand.RegisterOrder(9L, "카드",  "아은지", "010-0000-0009", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_10 = new OrderCommand.RegisterOrder(10L, "카드",  "자은지", "010-0000-00010", 주문한_상품_10_리스트);

        List<OrderCommand.RegisterOrder> orderLists = new ArrayList<>();
        orderLists.add(주문_생성_1);
        orderLists.add(주문_생성_2);
        orderLists.add(주문_생성_3);
        orderLists.add(주문_생성_4);
        orderLists.add(주문_생성_5);
        orderLists.add(주문_생성_6);
        orderLists.add(주문_생성_7);
        orderLists.add(주문_생성_8);
        orderLists.add(주문_생성_9);
        orderLists.add(주문_생성_10);

        for (int i = 0; i < threadCount; i++) {
            System.out.println("Thread " + Thread.currentThread().getId() + " is doing its work...");

            int n = i;
            executorService.submit(() -> {
                String orderToken = orderEntryService.registerOrderWithOptimisticLock(orderLists.get(n));
                latch.countDown();
            });

            System.out.println("Thread " + Thread.currentThread().getId() + " has finished its work");
        }

        latch.await();
        Thread.sleep(10000);

        em.flush();
        em.clear();

        // then
        ItemInfo.Main itemInfo = itemOneService.retrieveItemInfo(itemToken);
        assertEquals(0L, itemInfo.getItemStock());
    }

    @Test
    @DisplayName("Named Lock을 사용한다.")
    public void 동시에_10개의요청_테스트_3() throws InterruptedException {
        //given
        final int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // When
        String itemToken = itemEntryService.registerItem(판매준비중_단계의_상품_생성);

        OrderCommand.RegisterOrderItem 주문한_상품_1_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_2_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_3_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_4_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_5_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_6_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_7_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_8_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_9_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_10_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_1_리스트 = new ArrayList<>();
        주문한_상품_1_리스트.add(주문한_상품_1_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_2_리스트 = new ArrayList<>();
        주문한_상품_2_리스트.add(주문한_상품_2_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_3_리스트 = new ArrayList<>();
        주문한_상품_3_리스트.add(주문한_상품_3_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_4_리스트 = new ArrayList<>();
        주문한_상품_4_리스트.add(주문한_상품_4_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_5_리스트 = new ArrayList<>();
        주문한_상품_5_리스트.add(주문한_상품_5_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_6_리스트 = new ArrayList<>();
        주문한_상품_6_리스트.add(주문한_상품_6_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_7_리스트 = new ArrayList<>();
        주문한_상품_7_리스트.add(주문한_상품_7_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_8_리스트 = new ArrayList<>();
        주문한_상품_8_리스트.add(주문한_상품_8_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_9_리스트 = new ArrayList<>();
        주문한_상품_9_리스트.add(주문한_상품_9_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_10_리스트 = new ArrayList<>();
        주문한_상품_10_리스트.add(주문한_상품_10_생성);

        OrderCommand.RegisterOrder 주문_생성_1 = new OrderCommand.RegisterOrder(1L, "카드", "심은지", "010-0000-0001", 주문한_상품_1_리스트);
        OrderCommand.RegisterOrder 주문_생성_2 = new OrderCommand.RegisterOrder(2L, "카드", "가은지", "010-0000-0002", 주문한_상품_2_리스트);
        OrderCommand.RegisterOrder 주문_생성_3 = new OrderCommand.RegisterOrder(3L, "카드",  "나은지", "010-0000-0003", 주문한_상품_3_리스트);
        OrderCommand.RegisterOrder 주문_생성_4 = new OrderCommand.RegisterOrder(4L, "카드",  "다은지", "010-0000-0004", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_5 = new OrderCommand.RegisterOrder(5L, "카드",  "라은지", "010-0000-0005", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_6 = new OrderCommand.RegisterOrder(6L, "카드",  "마은지", "010-0000-0006", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_7 = new OrderCommand.RegisterOrder(7L, "카드",  "바은지", "010-0000-0007", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_8 = new OrderCommand.RegisterOrder(8L, "카드",  "사은지", "010-0000-0008", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_9 = new OrderCommand.RegisterOrder(9L, "카드",  "아은지", "010-0000-0009", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_10 = new OrderCommand.RegisterOrder(10L, "카드",  "자은지", "010-0000-00010", 주문한_상품_10_리스트);

        List<OrderCommand.RegisterOrder> orderLists = new ArrayList<>();
        orderLists.add(주문_생성_1);
        orderLists.add(주문_생성_2);
        orderLists.add(주문_생성_3);
        orderLists.add(주문_생성_4);
        orderLists.add(주문_생성_5);
        orderLists.add(주문_생성_6);
        orderLists.add(주문_생성_7);
        orderLists.add(주문_생성_8);
        orderLists.add(주문_생성_9);
        orderLists.add(주문_생성_10);

        for (int i = 0; i < threadCount; i++) {
            System.out.println("Thread " + Thread.currentThread().getId() + " is doing its work...");

            int n = i;
            executorService.submit(() -> {
                String orderToken = orderEntryService.registerOrderWithNamedLock(orderLists.get(n));
                latch.countDown();
            });

            System.out.println("Thread " + Thread.currentThread().getId() + " has finished its work");
        }

        latch.await();

        em.flush();
        em.clear();

        // then
        ItemInfo.Main itemInfo = itemOneService.retrieveItemInfo(itemToken);
        assertEquals(0L, itemInfo.getItemStock());
    }

    @Test
    @DisplayName("Lettuce")
    public void 동시에_10개의요청_테스트_with_Redis() throws InterruptedException {
        // when
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(threadCount);

        String itemToken = itemEntryService.registerItem(판매준비중_단계의_상품_생성);

        Stock stock = new Stock(itemToken, 10L);
        stockRepository.saveAndFlush(stock);

        // given
        OrderCommand.RegisterOrderItem 주문한_상품_1_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_2_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_3_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_4_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_5_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_6_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_7_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_8_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_9_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_10_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_1_리스트 = new ArrayList<>();
        주문한_상품_1_리스트.add(주문한_상품_1_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_2_리스트 = new ArrayList<>();
        주문한_상품_2_리스트.add(주문한_상품_2_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_3_리스트 = new ArrayList<>();
        주문한_상품_3_리스트.add(주문한_상품_3_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_4_리스트 = new ArrayList<>();
        주문한_상품_4_리스트.add(주문한_상품_4_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_5_리스트 = new ArrayList<>();
        주문한_상품_5_리스트.add(주문한_상품_5_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_6_리스트 = new ArrayList<>();
        주문한_상품_6_리스트.add(주문한_상품_6_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_7_리스트 = new ArrayList<>();
        주문한_상품_7_리스트.add(주문한_상품_7_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_8_리스트 = new ArrayList<>();
        주문한_상품_8_리스트.add(주문한_상품_8_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_9_리스트 = new ArrayList<>();
        주문한_상품_9_리스트.add(주문한_상품_9_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_10_리스트 = new ArrayList<>();
        주문한_상품_10_리스트.add(주문한_상품_10_생성);

        OrderCommand.RegisterOrder 주문_생성_1 = new OrderCommand.RegisterOrder(1L, "카드", "심은지", "010-0000-0001", 주문한_상품_1_리스트);
        OrderCommand.RegisterOrder 주문_생성_2 = new OrderCommand.RegisterOrder(2L, "카드", "가은지", "010-0000-0002", 주문한_상품_2_리스트);
        OrderCommand.RegisterOrder 주문_생성_3 = new OrderCommand.RegisterOrder(3L, "카드",  "나은지", "010-0000-0003", 주문한_상품_3_리스트);
        OrderCommand.RegisterOrder 주문_생성_4 = new OrderCommand.RegisterOrder(4L, "카드",  "다은지", "010-0000-0004", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_5 = new OrderCommand.RegisterOrder(5L, "카드",  "라은지", "010-0000-0005", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_6 = new OrderCommand.RegisterOrder(6L, "카드",  "마은지", "010-0000-0006", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_7 = new OrderCommand.RegisterOrder(7L, "카드",  "바은지", "010-0000-0007", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_8 = new OrderCommand.RegisterOrder(8L, "카드",  "사은지", "010-0000-0008", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_9 = new OrderCommand.RegisterOrder(9L, "카드",  "아은지", "010-0000-0009", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_10 = new OrderCommand.RegisterOrder(10L, "카드",  "자은지", "010-0000-00010", 주문한_상품_10_리스트);

        List<OrderCommand.RegisterOrder> orderLists = new ArrayList<>();
        orderLists.add(주문_생성_1);
        orderLists.add(주문_생성_2);
        orderLists.add(주문_생성_3);
        orderLists.add(주문_생성_4);
        orderLists.add(주문_생성_5);
        orderLists.add(주문_생성_6);
        orderLists.add(주문_생성_7);
        orderLists.add(주문_생성_8);
        orderLists.add(주문_생성_9);
        orderLists.add(주문_생성_10);

        for (int i = 0; i < threadCount; i++) {
            int n = i;
            executorService.submit(() -> {
                try {
                    String orderToken = orderEntryService.registerOrderWithLettuce(orderLists.get(n));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        em.flush();
        em.clear();

        // then
        Stock updatedStock = stockRepository.findByItemToken(itemToken).orElseThrow();
        assertEquals(0, updatedStock.getQuantity());
    }

    @Test
    @DisplayName("Redisson")
    public void 동시에_10개의요청_테스트_with_Redisson() throws InterruptedException {
        // when
        int threadCount = 10;
        ExecutorService executorService = Executors.newFixedThreadPool(3);
        CountDownLatch latch = new CountDownLatch(threadCount);

        // given
        String itemToken = itemEntryService.registerItem(판매준비중_단계의_상품_생성);

        System.out.println("itemToken" + itemToken);

//        Stock stock = new Stock(itemToken, 10L);
//        stockRepository.saveAndFlush(stock);

        OrderCommand.RegisterOrderItem 주문한_상품_1_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_2_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_3_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_4_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_5_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_6_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_7_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_8_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_9_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);
        OrderCommand.RegisterOrderItem 주문한_상품_10_생성 = new OrderCommand.RegisterOrderItem(1, itemToken, "제주해비치", 100000L);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_1_리스트 = new ArrayList<>();
        주문한_상품_1_리스트.add(주문한_상품_1_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_2_리스트 = new ArrayList<>();
        주문한_상품_2_리스트.add(주문한_상품_2_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_3_리스트 = new ArrayList<>();
        주문한_상품_3_리스트.add(주문한_상품_3_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_4_리스트 = new ArrayList<>();
        주문한_상품_4_리스트.add(주문한_상품_4_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_5_리스트 = new ArrayList<>();
        주문한_상품_5_리스트.add(주문한_상품_5_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_6_리스트 = new ArrayList<>();
        주문한_상품_6_리스트.add(주문한_상품_6_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_7_리스트 = new ArrayList<>();
        주문한_상품_7_리스트.add(주문한_상품_7_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_8_리스트 = new ArrayList<>();
        주문한_상품_8_리스트.add(주문한_상품_8_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_9_리스트 = new ArrayList<>();
        주문한_상품_9_리스트.add(주문한_상품_9_생성);

        List<OrderCommand.RegisterOrderItem> 주문한_상품_10_리스트 = new ArrayList<>();
        주문한_상품_10_리스트.add(주문한_상품_10_생성);

        OrderCommand.RegisterOrder 주문_생성_1 = new OrderCommand.RegisterOrder(1L, "카드", "심은지", "010-0000-0001", 주문한_상품_1_리스트);
        OrderCommand.RegisterOrder 주문_생성_2 = new OrderCommand.RegisterOrder(2L, "카드", "가은지", "010-0000-0002", 주문한_상품_2_리스트);
        OrderCommand.RegisterOrder 주문_생성_3 = new OrderCommand.RegisterOrder(3L, "카드",  "나은지", "010-0000-0003", 주문한_상품_3_리스트);
        OrderCommand.RegisterOrder 주문_생성_4 = new OrderCommand.RegisterOrder(4L, "카드",  "다은지", "010-0000-0004", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_5 = new OrderCommand.RegisterOrder(5L, "카드",  "라은지", "010-0000-0005", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_6 = new OrderCommand.RegisterOrder(6L, "카드",  "마은지", "010-0000-0006", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_7 = new OrderCommand.RegisterOrder(7L, "카드",  "바은지", "010-0000-0007", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_8 = new OrderCommand.RegisterOrder(8L, "카드",  "사은지", "010-0000-0008", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_9 = new OrderCommand.RegisterOrder(9L, "카드",  "아은지", "010-0000-0009", 주문한_상품_10_리스트);
        OrderCommand.RegisterOrder 주문_생성_10 = new OrderCommand.RegisterOrder(10L, "카드",  "자은지", "010-0000-00010", 주문한_상품_10_리스트);

        List<OrderCommand.RegisterOrder> orderLists = new ArrayList<>();
        orderLists.add(주문_생성_1);
        orderLists.add(주문_생성_2);
        orderLists.add(주문_생성_3);
        orderLists.add(주문_생성_4);
        orderLists.add(주문_생성_5);
        orderLists.add(주문_생성_6);
        orderLists.add(주문_생성_7);
        orderLists.add(주문_생성_8);
        orderLists.add(주문_생성_9);
        orderLists.add(주문_생성_10);

        for (int i = 0; i < threadCount; i++) {
            int n = i;
            executorService.submit(() -> {
                try {
                    orderEntryService.registerOrderWithRedission(orderLists.get(n));
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        em.flush();
        em.clear();

        // then
//        String key = itemToken + "stock:total";
//        ValueOperations<String, String> setOperations = redisTemplate.opsForValue();
//        String stock = (String) redisTemplate.opsForValue().get(key);

        ZSetOperations<String, Object> zSetOperations = redisTemplate.opsForZSet();
        System.out.println(zSetOperations.range("ZKey", 0, -1));

//        Stock updatedStock = stockRepository.findByItemToken(itemToken).orElseThrow();
//        assertEquals(0, stock);
    }

}