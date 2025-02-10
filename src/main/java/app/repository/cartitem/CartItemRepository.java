package app.repository.cartitem;

import app.model.CartItem;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    @Query("FROM CartItem cart "
            + "left join fetch cart.shoppingCart "
            + "left join fetch cart.shoppingCart.user "
            + "left join fetch cart.shoppingCart.user.roles "
            + "left join fetch cart.book "
            + "left join fetch cart.book.categories "
            + "WHERE cart.book.id = :bookId "
            + "AND cart.shoppingCart.id = :shoppingCartId")
    Optional<CartItem> findByBookIdAndShoppingCartId(
            @NonNull Long bookId, @NonNull Long shoppingCartId);
}
