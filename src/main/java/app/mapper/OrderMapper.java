package app.mapper;

import app.config.MapperConfig;
import app.dto.order.OrderDto;
import app.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {OrderItemMapper.class, UserMapper.class})
public interface OrderMapper {
    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "orderItems", target = "orderItemDtos",
            qualifiedByName = "fromOrderItemsToOrderItemDtos")
    OrderDto toDto(Order order);

    @Mapping(target = "user", ignore = true)
    @Mapping(source = "orderItemDtos", target = "orderItems",
            qualifiedByName = "fromOrderItemDtosToOrderItems")
    Order toModel(OrderDto orderDto);
}
