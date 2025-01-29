package app.repository.item.cart;

import app.model.ItemCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemCartRepository extends JpaRepository<ItemCart, Long> {
    @Query("FROM ItemCart cart "
            + "left join fetch cart.shoppingCart "
            + "left join fetch cart.shoppingCart.user "
            + "left join fetch cart.shoppingCart.user.roles "
            + "left join fetch cart.book "
            + "left join fetch cart.book.categories "
            + "WHERE cart.book.id = :bookId "
            + "AND cart.shoppingCart.id = :shoppingCartId")
    Optional<ItemCart> findByBookIdAndShoppingCartId(Long bookId, Long shoppingCartId);
}
