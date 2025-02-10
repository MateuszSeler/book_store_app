package app.repository.shoppingcart;

import app.model.ShoppingCart;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ShoppingCartRepository extends JpaRepository<ShoppingCart, Long> {
    @Query("FROM ShoppingCart cart "
            + "left join fetch cart.cartItems "
            + "left join fetch cart.cartItems.book "
            + "left join fetch cart.cartItems.book.categories "
            + "left join fetch cart.user "
            + "left join fetch cart.user.roles "
            + "WHERE cart.id = :id")
    Optional<ShoppingCart> findByIdWithAllData(@NonNull Long id);

    @Query("FROM ShoppingCart cart "
            + "left join fetch cart.cartItems "
            + "left join fetch cart.cartItems.book "
            + "left join fetch cart.cartItems.book.categories "
            + "JOIN cart.user user "
            + "WHERE cart.id = :id")
    Optional<ShoppingCart> findByIdLimitingUserDataToId(@NonNull Long id);
}
