package app.service;

import app.dto.order.OrderDto;
import app.dto.orderitem.OrderItemDto;
import app.exception.EntityNotFoundException;
import app.mapper.OrderItemMapper;
import app.mapper.OrderMapper;
import app.model.CartItem;
import app.model.Order;
import app.model.OrderItem;
import app.model.ShoppingCart;
import app.repository.order.OrderRepository;
import app.repository.orderitem.OrderItemRepository;
import app.repository.shoppingcart.ShoppingCartRepository;
import app.repository.user.UserRepository;
import jakarta.validation.Valid;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import lombok.NonNull;
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
    private final UserRepository userRepository;

    @Override
    @Transactional
    public OrderDto save(@Valid Order order) {
        return orderMapper.toDto(orderRepository.save(order));
    }

    @Override
    @Transactional
    public OrderDto creatingOrderBasedOnUsersShoppingCart(@NonNull Long userId) {
        ShoppingCart shoppingCart = shoppingCartRepository
                .findByIdLimitingUserDataToId(userId)
                .orElseThrow(
                    () -> new EntityNotFoundException("ShoppingCart with id: "
                            + userId + " not found")
        );

        Order order = new Order();
        order.setUser(shoppingCart.getUser());
        order.setStatus(Order.OrderStatus.WAITING_FOR_CONFIRMATION);
        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(shoppingCart.getUser().getShippingAddress());

        order.setOrderItems(shoppingCart.getCartItems()
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
    public List<OrderDto> getOrderHistory(@NonNull Long userId) {
        return orderRepository.getOrdersByUserIdLimitingUserDataToId(userId)
                .stream()
                .map(orderMapper::toDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDto getOrderDetails(@NonNull Long orderId, @NonNull Long userId) {
        return orderMapper.toDto(orderRepository
                .getOrderByIdAndUserIdLimitingUserDataToId(orderId, userId)
                    .orElseThrow(
                            () -> new EntityNotFoundException("Order with id: "
                            + orderId + " and user id: " + userId + " not found")));
    }

    @Override
    @Transactional(readOnly = true)
    public OrderItemDto getItemDetailsFromTheOrder(
            @NonNull Long orderItemId, @NonNull Long userId) {
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

    @Override
    @Transactional(readOnly = true)
    public Order toEntity(OrderDto orderDto) {
        Order order = orderMapper.toModel(orderDto);
        order.setUser(
                userRepository.findById(orderDto.getUserId())
                        .orElseThrow(
                            () -> new EntityNotFoundException("User with id: "
                                    + orderDto.getUserId() + " not found")
                ));

        return order;
    }

    @Transactional
    private OrderItem convertItemCartIntoOrderItem(CartItem cartItem, Order order) {
        OrderItem orderItem = new OrderItem();
        orderItem.setOrder(order);
        orderItem.setBook(cartItem.getBook());
        if (cartItem.getQuantity() < 1) {
            throw new IllegalArgumentException("Quantity from cart item with id: "
                    + cartItem.getId()
                    + " is below minimal level");
        }
        orderItem.setQuantity(cartItem.getQuantity());

        orderItem.setPrice(cartItem.getBook().getPrice().multiply(
                new BigDecimal(cartItem.getQuantity())));
        return orderItem;
    }
}
