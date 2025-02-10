package app.mapper;

import app.config.MapperConfig;
import app.dto.shoppingcart.ShoppingCartDto;
import app.model.ShoppingCart;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {CartItemMapper.class, UserMapper.class})
public interface ShoppingCartMapper {
    @Mapping(source = "cartItems", target = "cartItemDtos",
            qualifiedByName = "fromCartItemsToCartItemDtos")
    @Mapping(source = "id", target = "userId")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @Mapping(source = "cartItemDtos", target = "cartItems",
            qualifiedByName = "fromCartItemDtosToCartItems")
    @Mapping(target = "user", ignore = true)
    @Mapping(source = "userId", target = "id")
    ShoppingCart toModel(
            ShoppingCartDto shoppingCartDto);

}
