package app.service;

import app.dto.itemcart.ItemCartCreateRequestDto;
import app.dto.shoppingcart.ShoppingCartDto;
import app.exception.EntityNotFoundException;
import app.mapper.ShoppingCartMapper;
import app.model.Book;
import app.model.ItemCart;
import app.model.ShoppingCart;
import app.model.User;
import app.repository.book.BookRepository;
import app.repository.itemcart.ItemCartRepository;
import app.repository.shoppingcart.ShoppingCartRepository;
import app.repository.user.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class ShoppingCartServiceImpl implements ShoppingCartService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final UserRepository userRepository;
    private final ShoppingCartMapper shoppingCartMapper;
    private final ItemCartRepository itemCartRepository;
    private final BookRepository bookRepository;

    @Override
    @Transactional
    public ShoppingCartDto getOrCreateShoppingCart(Long id) {
        User user = getUserById(id);
        Optional<ShoppingCart> optionalShoppingCart = shoppingCartRepository.findById(id);

        if (optionalShoppingCart.isPresent()) {
            return shoppingCartMapper.toDto(optionalShoppingCart.get());
        } else {
            ShoppingCart newShoppingCart = new ShoppingCart();
            newShoppingCart.setUser(user);

            return save(newShoppingCart);
        }
    }

    @Override
    @Transactional
    public ShoppingCartDto save(ShoppingCart shoppingCart) {
        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    @Transactional
    public ShoppingCartDto addOrUpdateItemCart(
            ItemCartCreateRequestDto itemCartCreateRequestDto, Long userId) {

        ShoppingCart shoppingCart = shoppingCartMapper.toModel(
                getOrCreateShoppingCart(userId), userRepository);

        ItemCart itemCart = getOrCreateItemCart(itemCartCreateRequestDto, shoppingCart);

        shoppingCart.getItemCarts().add(itemCart);
        return save(shoppingCart);
    }

    @Override
    @Transactional
    public ShoppingCartDto deleteItemCartByBookId(Long bookId, Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("ShoppingCart with id: "
                        + userId + " not found"));

        ItemCart itemCart = findItemCartByBookIdAndShoppingCartId(bookId, userId).orElseThrow(
                () -> new EntityNotFoundException("ItemCart with bookId: "
                        + bookId + " for user with id: " + userId + " not found"));

        shoppingCart.getItemCarts().remove(itemCart);
        itemCartRepository.delete(itemCart);

        return shoppingCartMapper.toDto(shoppingCartRepository.save(shoppingCart));
    }

    @Override
    public void delete(ShoppingCart shoppingCart) {
        for (ItemCart itemCart : shoppingCart.getItemCarts()) {
            itemCartRepository.delete(itemCart);
        }

        shoppingCartRepository.delete(shoppingCart);
    }

    @Transactional(readOnly = true)
    private User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(
                        "User with id: " + id + " not found"));
    }

    @Transactional(readOnly = true)
    Optional<ItemCart> findItemCartByBookIdAndShoppingCartId(Long bookId, Long shoppingCartId) {
        return itemCartRepository.findByBookIdAndShoppingCartId(bookId, shoppingCartId);
    }

    @Transactional
    private ItemCart getOrCreateItemCart(
            ItemCartCreateRequestDto itemCartCreateRequestDto, ShoppingCart shoppingCart) {
        Optional<ItemCart> optionalItemCart =
                findItemCartByBookIdAndShoppingCartId(
                        itemCartCreateRequestDto.getBookId(), shoppingCart.getId());

        if (optionalItemCart.isPresent()) {
            ItemCart itemCart = optionalItemCart.get();
            itemCart.setQuantity(itemCartCreateRequestDto.getQuantity());
            return itemCartRepository.save(itemCart);
        } else {
            ItemCart itemCart = new ItemCart();
            itemCart.setShoppingCart(shoppingCart);
            Book book = bookRepository.findById(itemCartCreateRequestDto.getBookId()).orElseThrow(
                    () -> new EntityNotFoundException("Book with id: "
                            + itemCartCreateRequestDto.getBookId() + " not found"));
            itemCart.setBook(book);
            itemCart.setQuantity(itemCartCreateRequestDto.getQuantity());
            return itemCartRepository.save(itemCart);
        }
    }
}
