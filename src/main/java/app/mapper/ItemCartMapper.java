package app.mapper;

import app.config.MapperConfig;
import app.dto.itemcart.ItemCartDto;
import app.model.ItemCart;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = {BookMapper.class})
public interface ItemCartMapper {
    @Mapping(source = "book", target = "bookDto",
            qualifiedByName = "fromBookToBookDto")
    ItemCartDto toDto(ItemCart itemCart);

    ItemCart toModel(ItemCartDto itemCartDto);

    @Named("fromItemCartDtosToItemCarts")
    default Set<ItemCart> itemCartSet(
            Set<ItemCartDto> itemCartsDtos) {
        if (itemCartsDtos == null || itemCartsDtos.isEmpty()) {
            return new HashSet<>();
        }
        return itemCartsDtos
                .stream()
                .map(this::toModel)
                .collect(Collectors.toSet());
    }

    @Named("fromItemCartsToItemCartDtos")
    default Set<ItemCartDto> itemCartDtosSet(Set<ItemCart> itemCarts) {
        if (itemCarts == null || itemCarts.isEmpty()) {
            return new HashSet<>();
        }
        return itemCarts
                .stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

}
