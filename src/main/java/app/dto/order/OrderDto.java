package app.dto.order;

import app.dto.orderitem.OrderItemDto;
import app.model.Order;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;
import lombok.Data;

@Data
public class OrderDto {
    @NotNull
    @Positive
    private Long id;
    @NotNull
    @Positive
    private Long userId;
    private Order.OrderStatus status;
    @DecimalMin("0.00")
    private BigDecimal total;
    private LocalDateTime orderDate;
    private String shippingAddress;
    private Set<OrderItemDto> orderItemDtos;
}
