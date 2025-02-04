package app.service;

import app.dto.order.OrderDto;
import app.dto.orderitem.OrderItemDto;
import app.exception.EntityNotFoundException;
import app.mapper.OrderItemMapper;
import app.mapper.OrderMapper;
import app.model.ItemCart;
import app.model.Order;
import app.model.OrderItem;
import app.model.ShoppingCart;
import app.repository.order.OrderRepository;
import app.repository.orderitem.OrderItemRepository;
import app.repository.shoppingcart.ShoppingCartRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final ShoppingCartRepository shoppingCartRepository;
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderMapper orderMapper;
    private final OrderItemMapper orderItemMapper;
    private final ShoppingCartService shoppingCartService;

    @Override
    @Transactional
    public OrderDto save(Order order) {
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderDto placeOrder(Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository.findById(userId).orElseThrow(
                () -> new EntityNotFoundException("ShoppingCart with id: "
                        + userId + " not found")
        );

        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setStatus(Order.OrderStatus.WAITING_FOR_CONFIRMATION);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(shoppingCart.getUser().getShippingAddress());
        order.setTotal(BigDecimal.ZERO);
        save(order);
        order.setOrderItems(shoppingCart.getItemCarts()
                .stream()
                .map(i -> convertItemCartIntoOrderItem(i, order))
                .collect(Collectors.toSet()));
        order.setTotal(order.getOrderItems()
                .stream()
                .map(OrderItem::getPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add));

        shoppingCartService.delete(shoppingCart);
        return save(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDto> getOrderHistory(Long userId) {
        return orderRepository.getOrdersByUserId(userId)
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto getOrderDetails(Long orderId, Long userId) {
        return orderMapper.toDto(orderRepository.getOrderByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new EntityNotFoundException("Order with id: "
                        + orderId + " and user id: " + userId + " not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItemDto getItemDetailsFromTheOrder(Long orderItemId, Long userId) {
        return orderItemMapper.toDto(orderItemRepository.getByIdAndUserId(orderItemId, userId));
    }

    @Override
    @Transactional
    public OrderDto updateOrderStatus(Long orderId, String orderStatus) {
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new EntityNotFoundException(
                        "Order with id: " + orderId + " not found"));

        try {
            order.setStatus(Order.OrderStatus.valueOf(orderStatus));

        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException(
                    "Invalid data format of order status value: " + orderStatus);
        }
        return save(order);
    }

    @Transactional
    private OrderItem convertItemCartIntoOrderItem(ItemCart itemCart, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBook(itemCart.getBook());
        orderItem.setQuantity(itemCart.getQuantity());
        orderItem.setPrice(itemCart.getBook().getPrice().multiply(
                new BigDecimal(itemCart.getQuantity())));
        return orderItemRepository.save(orderItem);
    }
}
