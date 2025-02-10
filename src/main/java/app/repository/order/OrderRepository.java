package app.repository.order;

import app.model.Order;
import java.util.List;
import java.util.Optional;
import lombok.NonNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    @Query("FROM Order order "
            + "left join fetch order.orderItems "
            + "left join fetch order.orderItems.book "
            + "left join fetch order.orderItems.book.categories "
            + "left join fetch order.user "
            + "left join fetch order.user.roles "
            + "WHERE order.id = :id")
    Optional<Order> findById(
            @NonNull Long id);

    @Query("FROM Order order "
            + "JOIN FETCH order.orderItems orderItem "
            + "JOIN FETCH orderItem.book book "
            + "JOIN FETCH book.categories category "
            + "JOIN FETCH order.user user "
            + "JOIN FETCH user.roles role "
            + "WHERE user.id = :userId")
    List<Order> getOrdersByUserIdWithAllData(
            @NonNull Long userId);

    @Query("FROM Order order "
            + "JOIN FETCH order.orderItems orderItem "
            + "JOIN FETCH orderItem.book book "
            + "JOIN FETCH book.categories category "
            + "JOIN order.user user "
            + "WHERE user.id = :userId")
    List<Order> getOrdersByUserIdLimitingUserDataToId(
            @NonNull Long userId);

    @Query("FROM Order order "
            + "left join fetch order.orderItems "
            + "left join fetch order.orderItems.book "
            + "left join fetch order.orderItems.book.categories "
            + "left join fetch order.user "
            + "left join fetch order.user.roles "
            + "WHERE order.user.id = :userId "
            + "AND order.id = :orderId")
    Optional<Order> getOrderByIdAndUserIdWithAllData(
            @NonNull Long orderId, @NonNull Long userId);

    @Query("FROM Order order "
            + "JOIN FETCH order.orderItems orderItem "
            + "JOIN FETCH orderItem.book book "
            + "JOIN FETCH book.categories category "
            + "JOIN order.user user "
            + "WHERE order.user.id = :userId "
            + "AND order.id = :orderId")
    Optional<Order> getOrderByIdAndUserIdLimitingUserDataToId(
            @NonNull Long orderId, @NonNull Long userId);
}
