package app.mapper;

import app.config.MapperConfig;
import app.dto.cartitem.CartItemDto;
import app.model.CartItem;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class, uses = {BookMapper.class})
public interface CartItemMapper {
    @Mapping(source = "book", target = "bookDto",
            qualifiedByName = "fromBookToBookDto")
    CartItemDto toDto(CartItem cartItem);

    CartItem toModel(CartItemDto cartItemDto);

    @Named("fromCartItemDtosToCartItems")
    default Set<CartItem> cartItemsSet(
            Set<CartItemDto> cartItemsDtos) {
        if (cartItemsDtos == null || cartItemsDtos.isEmpty()) {
            return new HashSet<>();
        }
        return cartItemsDtos
                .stream()
                .map(this::toModel)
                .collect(Collectors.toSet());
    }

    @Named("fromCartItemsToCartItemDtos")
    default Set<CartItemDto> cartItemDtosSet(Set<CartItem> cartItems) {
        if (cartItems == null || cartItems.isEmpty()) {
            return new HashSet<>();
        }
        return cartItems
                .stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }

}
