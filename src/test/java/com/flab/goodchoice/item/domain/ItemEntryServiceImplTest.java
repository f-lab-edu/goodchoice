package com.flab.goodchoice.item.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
@SpringBootTest
@Transactional
class ItemEntryServiceImplTest {

    ItemCommand.RegisterItemRequest 판매준비중_단계의_상품_생성 = new ItemCommand.RegisterItemRequest("제주해비치", 100000L, 10L);

    @Autowired
    private ItemEntryServiceImpl itemEntryServiceImpl;

    @Autowired
    private ItemOneServiceImpl itemOneServiceImpl;

    @Autowired
    private EntityManager em;

    @BeforeEach
    void setUp() {

    }

    @DisplayName("상품을 생성한다.")
    @Test
    void create() {
        // when
        String itemToken = itemEntryServiceImpl.registerItem(판매준비중_단계의_상품_생성);

        em.flush();

        ItemInfo.Main itemInfo = itemOneServiceImpl.retrieveItemInfo(itemToken);

        // then
        assertEquals(판매준비중_단계의_상품_생성.getItemName(), itemInfo.getItemName());
        assertEquals(판매준비중_단계의_상품_생성.getItemPrice(), itemInfo.getItemPrice());
        assertEquals(판매준비중_단계의_상품_생성.getItemStock(), itemInfo.getItemStock());
        assertEquals(Item.Status.PREPARE, itemInfo.getStatus());
    }

}