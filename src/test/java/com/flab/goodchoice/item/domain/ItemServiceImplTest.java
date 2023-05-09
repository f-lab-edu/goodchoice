package com.flab.goodchoice.item.domain;

import com.flab.goodchoice.item.infrastructure.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;


@ActiveProfiles("test")
@SpringBootTest
@Transactional
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_CLASS)
class ItemServiceImplTest {

    ItemCommand.RegisterItemRequest 판매준비중_단계의_상품_생성 = new ItemCommand.RegisterItemRequest("제주해비치", 100000L, 10L);

    ItemCommand.UpdateItemRequest 판매준비중_단계의_상품_상품명_업데이트 = new ItemCommand.UpdateItemRequest("제주해비치 업데이트", 100000L, 10L);

    ItemCommand.UpdateItemRequest 판매준비중_단계의_상품_가격_업데이트 = new ItemCommand.UpdateItemRequest("제주해비치", 99999L, 10L);

    ItemCommand.UpdateItemRequest 판매준비중_단계의_상품_재고_업데이트 = new ItemCommand.UpdateItemRequest("제주해비치", 100000L, 1L);

    @Autowired
    private ItemServiceImpl itemServiceImpl;

    @Autowired
    private ItemRepository itemRepository;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void setUp() {

    }

    @DisplayName("상품을 생성한다.")
    @Test
    void create() {
        // when
        String itemToken = itemServiceImpl.registerItem(판매준비중_단계의_상품_생성);

        em.flush();

        ItemInfo.Main itemInfo = itemServiceImpl.retrieveItemInfo(itemToken);

        // then
        assertEquals(판매준비중_단계의_상품_생성.getItemName(), itemInfo.getItemName());
        assertEquals(판매준비중_단계의_상품_생성.getItemPrice(), itemInfo.getItemPrice());
        assertEquals(판매준비중_단계의_상품_생성.getItemStock(), itemInfo.getItemStock());
        assertEquals(Item.Status.PREPARE, itemInfo.getStatus());
    }

    @DisplayName("상품을 수정한다 - 상품명 하나만 변경")
    @Test
    void updateItemName() {
        // when
        String itemToken = itemServiceImpl.registerItem(판매준비중_단계의_상품_생성);

        em.flush();

        ItemInfo.Main itemInfo = itemServiceImpl.updateItem(itemToken, 판매준비중_단계의_상품_상품명_업데이트);

        // then
        assertEquals(판매준비중_단계의_상품_상품명_업데이트.getItemName(), itemInfo.getItemName());
        assertEquals(판매준비중_단계의_상품_상품명_업데이트.getItemPrice(), itemInfo.getItemPrice());
        assertEquals(판매준비중_단계의_상품_상품명_업데이트.getItemStock(), itemInfo.getItemStock());
    }

    @DisplayName("상품을 수정한다 - 상품가격 하나만 변경")
    @Test
    void updateItemPrice() {
        // when
        String itemToken = itemServiceImpl.registerItem(판매준비중_단계의_상품_생성);

        em.flush();

        ItemInfo.Main itemInfo = itemServiceImpl.updateItem(itemToken, 판매준비중_단계의_상품_가격_업데이트);

        // then
        assertEquals(판매준비중_단계의_상품_가격_업데이트.getItemName(), itemInfo.getItemName());
        assertEquals(판매준비중_단계의_상품_가격_업데이트.getItemPrice(), itemInfo.getItemPrice());
        assertEquals(판매준비중_단계의_상품_가격_업데이트.getItemStock(), itemInfo.getItemStock());
    }

    @DisplayName("상품을 수정한다 - 상품재고 하나만 변경")
    @Test
    void updateItemStock() {
        // when
        String itemToken = itemServiceImpl.registerItem(판매준비중_단계의_상품_생성);

        em.flush();

        ItemInfo.Main itemInfo = itemServiceImpl.updateItem(itemToken, 판매준비중_단계의_상품_재고_업데이트);

        // then
        assertEquals(판매준비중_단계의_상품_재고_업데이트.getItemName(), itemInfo.getItemName());
        assertEquals(판매준비중_단계의_상품_재고_업데이트.getItemPrice(), itemInfo.getItemPrice());
        assertEquals(판매준비중_단계의_상품_재고_업데이트.getItemStock(), itemInfo.getItemStock());
    }
}