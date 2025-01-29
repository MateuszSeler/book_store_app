package app.service;

import app.dto.item.cart.ItemCartCreateRequestDto;
import app.dto.shopping.cart.ShoppingCartDto;
import app.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCartDto getOrCreateShoppingCart(Long id);

    ShoppingCartDto save(ShoppingCart shoppingCart);

    ShoppingCartDto addOrUpdateItemCart(
            ItemCartCreateRequestDto itemCartCreateRequestDto, Long userId);

    ShoppingCartDto deleteItemCartByBookId(Long bookId, Long userId);
}
