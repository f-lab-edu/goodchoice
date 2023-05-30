package com.flab.goodchoice.order.interfaces;

import com.flab.goodchoice.order.domain.OrderCommand;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface OrderDtoMapper {
    OrderCommand.RegisterOrder of(OrderDto.RegisterOrderRequest request);

    OrderDto.RegisterResponse of(String orderToken);
}
