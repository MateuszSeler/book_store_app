package app.controller;

import app.dto.order.OrderDto;
import app.dto.orderitem.OrderItemDto;
import app.service.OrderService;
import app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "categories manager",
        description = "Endpoints for managing categories in book shop app")
@RequiredArgsConstructor
@RestController
@RequestMapping("/orders")
public class OrderController {
    private final OrderService orderService;
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping
    @Operation(summary = "placing new order",
            description = "placing new order")
    public OrderDto placeOrder() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return orderService.placeOrder(
                userService.findByEmail(authentication.getName()).getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @Operation(summary = "getting order history",
            description = "getting order history")
    public List<OrderDto> getOrderHistory() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return orderService.getOrderHistory(
                userService.findByEmail(authentication.getName()).getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{orderId}")
    @Operation(summary = "getting order",
            description = "getting details about order by orderId")
    public OrderDto getOrderDetails(@PathVariable Long orderId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return orderService.getOrderDetails(orderId,
                userService.findByEmail(authentication.getName()).getId());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{orderId}/items/{orderItemId}")
    @Operation(summary = "getting order history",
            description = "getting order history")
    public OrderItemDto getItemDetailsFromTheOrder(
            @PathVariable Long orderId, @PathVariable Long orderItemId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return orderService.getItemDetailsFromTheOrder(orderItemId,
                userService.findByEmail(authentication.getName()).getId());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{orderId}/{orderStatus}")
    @Operation(summary = "updating order status",
            description = "updating order status")
    public OrderDto updateOrderStatus(
            @PathVariable Long orderId, @PathVariable String orderStatus) {
        return orderService.updateOrderStatus(orderId, orderStatus);
    }
}
