package app.service;

import app.dto.itemcart.ItemCartCreateRequestDto;
import app.dto.shoppingcart.ShoppingCartDto;
import app.model.ShoppingCart;

public interface ShoppingCartService {
    ShoppingCartDto getOrCreateShoppingCart(Long id);

    ShoppingCartDto save(ShoppingCart shoppingCart);

    ShoppingCartDto addOrUpdateItemCart(
            ItemCartCreateRequestDto itemCartCreateRequestDto, Long userId);

    ShoppingCartDto deleteItemCartByBookId(Long bookId, Long userId);

    void delete(ShoppingCart shoppingCart);
}
