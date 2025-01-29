package app.repository.shopping.cart;

import app.model.ShoppingCart;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("FROM ShoppingCart cart "
            + "left join fetch cart.itemCarts "
            + "left join fetch cart.itemCarts.book "
            + "left join fetch cart.itemCarts.book.categories "
            + "left join fetch cart.user "
            + "left join fetch cart.user.roles "
            + "WHERE cart.id = :id")
    Optional<ShoppingCart> findById(Long id);
}
