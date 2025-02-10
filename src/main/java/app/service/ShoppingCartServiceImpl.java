package app.service;

import app.dto.cartitem.CartItemCreateRequestDto;
import app.dto.shoppingcart.ShoppingCartDto;
import app.exception.EntityNotFoundException;
import app.mapper.ShoppingCartMapper;
import app.model.Book;
import app.model.CartItem;
import app.model.ShoppingCart;
import app.model.User;
import app.repository.book.BookRepository;
import app.repository.cartitem.CartItemRepository;
import app.repository.shoppingcart.ShoppingCartRepository;
import app.repository.user.UserRepository;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.Optional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final CartItemRepository cartItemRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public ShoppingCartDto get(@NonNull Long id) {
        return shoppingCartMapper.toDto(getOrCreateShoppingCart(id));
    }

    @Override
    @Transactional
    public ShoppingCartDto save(@Valid ShoppingCart shoppingCart) {
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    @Transactional
    public ShoppingCartDto addOrUpdateItemCart(
            @Valid CartItemCreateRequestDto cartItemCreateRequestDto, @NonNull Long userId) {

        ShoppingCart shoppingCart = getOrCreateShoppingCart(userId);

        CartItem cartItem = getOrCreateItemCart(cartItemCreateRequestDto, shoppingCart);

        shoppingCart.getCartItems().add(cartItem);
        return save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto deleteItemCartByBookId(@NonNull Long bookId, @NotNull Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByIdLimitingUserDataToId(userId)
                    .orElseThrow(
                        () -> new EntityNotFoundException("ShoppingCart with id: "
                                + userId + " not found"));

        CartItem cartItem = findItemCartByBookIdAndShoppingCartId(bookId, userId).orElseThrow(
                () -> new EntityNotFoundException("ItemCart with bookId: "
                        + bookId + " for user with id: " + userId + " not found"));

        shoppingCart.getCartItems().remove(cartItem);

        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public ShoppingCart toEntity(ShoppingCartDto shoppingCartDto) {
        ShoppingCart shoppingCart = shoppingCartMapper.toModel(shoppingCartDto);
        shoppingCart.setUser(
                userRepository.findById(shoppingCartDto.getUserId())
                        .orElseThrow(
                            () -> new EntityNotFoundException(
                                    "User with id: "
                                    + shoppingCartDto.getUserId() + " not found")));

        return shoppingCart;
    }

    @Override
    public void delete(ShoppingCart shoppingCart) {
        shoppingCartRepository.delete(shoppingCart);
    }

    @Transactional(readOnly = true)
    private User getUserById(@NonNull Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with id: " + id + " not found"));
    }

    @Transactional(readOnly = true)
    Optional<CartItem> findItemCartByBookIdAndShoppingCartId(
            @NonNull Long bookId, @NonNull Long shoppingCartId) {
        return cartItemRepository.findByBookIdAndShoppingCartId(bookId, shoppingCartId);
    }

    @Transactional
    private ShoppingCart getOrCreateShoppingCart(@NonNull Long id) {
        User user = getUserById(id);
        Optional<ShoppingCart> optionalShoppingCart =
                shoppingCartRepository.findByIdLimitingUserDataToId(id);

        if (optionalShoppingCart.isPresent()) {
            return optionalShoppingCart.get();
        } else {
            ShoppingCart newShoppingCart = new ShoppingCart();
            newShoppingCart.setUser(user);

            return shoppingCartRepository.save(newShoppingCart);
        }
    }

    @Transactional
    private CartItem getOrCreateItemCart(
            @Valid CartItemCreateRequestDto cartItemCreateRequestDto,
            @Valid ShoppingCart shoppingCart) {
        Optional<CartItem> optionalItemCart =
                findItemCartByBookIdAndShoppingCartId(
                        cartItemCreateRequestDto.getBookId(), shoppingCart.getId());

        if (optionalItemCart.isPresent()) {
            CartItem cartItem = optionalItemCart.get();
            cartItem.setQuantity(cartItemCreateRequestDto.getQuantity());
            return cartItem;
        } else {
            CartItem cartItem = new CartItem();
            cartItem.setShoppingCart(shoppingCart);
            Book book = bookRepository.findById(cartItemCreateRequestDto.getBookId()).orElseThrow(
                    () -> new EntityNotFoundException("Book with id: "
                            + cartItemCreateRequestDto.getBookId() + " not found"));
            cartItem.setBook(book);
            cartItem.setQuantity(cartItemCreateRequestDto.getQuantity());
            return cartItem;
        }
    }
}
