package com.flab.goodchoice.item.infrastructure;

import com.flab.goodchoice.item.domain.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.LockModeType;
import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Long> {

    Optional<Item> findByItemToken(String itemToken);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from Item i where i.itemToken=:itemToken")
    Optional<Item> findByItemTokenWithPessimisticLock(String itemToken);

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select i from Item i where i.itemToken=:itemToken")
    Optional<Item> findByItemTokenWithOptimisticLock(String itemToken);

}
