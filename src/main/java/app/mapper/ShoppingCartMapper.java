package app.mapper;

import app.config.MapperConfig;
import app.dto.shoppingcart.ShoppingCartDto;
import app.model.ShoppingCart;
import app.repository.user.UserRepository;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {ItemCartMapper.class, UserMapper.class})
public interface ShoppingCartMapper {
    @Mapping(source = "itemCarts", target = "itemCartDtos",
            qualifiedByName = "fromItemCartsToItemCartDtos")
    @Mapping(source = "id", target = "userId")
    ShoppingCartDto toDto(ShoppingCart shoppingCart);

    @Mapping(source = "itemCartDtos", target = "itemCarts",
            qualifiedByName = "fromItemCartDtosToItemCarts")
    @Mapping(source = "userId", target = "user",
            qualifiedByName = "fromUserIdToUser")
    @Mapping(source = "userId", target = "id")
    ShoppingCart toModel(
            ShoppingCartDto shoppingCartDto, @Context UserRepository userRepository);

}
