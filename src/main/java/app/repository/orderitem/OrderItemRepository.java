package app.repository.orderitem;

import app.model.OrderItem;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    @Query("FROM OrderItem orderItem "
            + "left join fetch orderItem.order "
            + "left join fetch orderItem.book "
            + "left join fetch orderItem.book.categories "
            + "left join fetch orderItem.order.user "
            + "left join fetch orderItem.order.user.roles "
            + "WHERE orderItem.id = :id")
    Optional<OrderItem> findById(@NonNull Long id);

    @Query("FROM OrderItem orderItem "
            + "left join fetch orderItem.order "
            + "left join fetch orderItem.book "
            + "left join fetch orderItem.book.categories "
            + "left join fetch orderItem.order.user "
            + "left join fetch orderItem.order.user.roles "
            + "WHERE orderItem.id = :orderItemId "
            + "AND orderItem.order.user.id = :userId")
    OrderItem getByIdAndUserId(@NonNull Long orderItemId, @NonNull Long userId);
}
