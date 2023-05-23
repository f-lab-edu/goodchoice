package com.flab.goodchoice.item.interfaces;

import com.flab.goodchoice.item.domain.Item;
import com.flab.goodchoice.item.domain.ItemCommand;
import com.flab.goodchoice.item.domain.ItemInfo;
import org.mapstruct.*;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface ItemDtoMapper {

    ItemCommand.RegisterItemRequest of(ItemDto.RegisterItemRequest request);

    ItemCommand.UpdateItemRequest of(ItemDto.UpdateItemRequest request);

    ItemDto.RegisterResponse of(String itemToken);

    ItemDto.Main of(ItemInfo.Main main);

    List<ItemDto.Main> of(List<Item> main);

}

