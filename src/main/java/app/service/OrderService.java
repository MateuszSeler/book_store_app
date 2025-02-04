package app.service;

import app.dto.order.OrderDto;
import app.dto.orderitem.OrderItemDto;
import app.model.Order;
import java.util.List;

public interface OrderService {
    OrderDto save(Order order);

    OrderDto placeOrder(Long userId);

    List<OrderDto> getOrderHistory(Long userId);

    OrderDto getOrderDetails(Long orderId, Long userId);

    OrderItemDto getItemDetailsFromTheOrder(Long orderItemId, Long userId);

    OrderDto updateOrderStatus(Long orderId, String orderStatus);
}
