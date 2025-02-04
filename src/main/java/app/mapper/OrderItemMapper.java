package app.mapper;

import app.config.MapperConfig;
import app.dto.orderitem.OrderItemDto;
import app.model.OrderItem;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = {BookMapper.class})
public interface OrderItemMapper {
    @Named("fromOrderItemsToOrderItemDtos")
    @Mapping(source = "book", target = "bookDto",
            qualifiedByName = "fromBookToBookDto")
    OrderItemDto toDto(OrderItem orderItem);

    OrderItem toModel(OrderItemDto orderItemDto);

    @Named("fromOrderItemDtosToOrderItems")
    default Set<OrderItem> orderItemSet(
            Set<OrderItemDto> orderItemDtos) {
        if (orderItemDtos == null || orderItemDtos.isEmpty()) {
            return new HashSet<>();
        }
        return orderItemDtos
                .stream()
                .map(this::toModel)
                .collect(Collectors.toSet());
    }

    @Named("fromOrderItemsToOrderItemDtos")
    default Set<OrderItemDto> orderItemDtosSet(Set<OrderItem> orderItems) {
        if (orderItems == null || orderItems.isEmpty()) {
            return new HashSet<>();
        }
        return orderItems
                .stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

}
