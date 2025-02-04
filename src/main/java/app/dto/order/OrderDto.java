package app.dto.order;

import app.dto.orderitem.OrderItemDto;
import app.model.Order;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderDto {
    private Long id;
    private Long userId;
    private Order.OrderStatus status;
    private BigDecimal total;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private Set<OrderItemDto> orderItemDtos;
}
