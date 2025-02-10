package app.service;

import app.dto.cartitem.CartItemCreateRequestDto;
import app.dto.shoppingcart.ShoppingCartDto;
import app.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCartDto get(Long id);

    ShoppingCartDto save(ShoppingCart shoppingCart);

    ShoppingCartDto addOrUpdateItemCart(
            CartItemCreateRequestDto cartItemCreateRequestDto, Long userId);

    ShoppingCartDto deleteItemCartByBookId(Long bookId, Long userId);

    ShoppingCart toEntity(ShoppingCartDto shoppingCartDto);

    void delete(ShoppingCart shoppingCart);
}
