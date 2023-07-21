package com.flab.goodchoiceapi.item.interfaces;

import com.flab.goodchoiceitem.domain.Item;
import com.flab.goodchoiceapi.item.domain.ItemCommand;
import com.flab.goodchoiceapi.item.domain.ItemInfo;
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

