package com.example.store.orders;

import com.example.store.auth.AuthService;
import com.example.store.users.User;
import lombok.AllArgsConstructor;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class OrderService {
    private final AuthService authService;
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public List<OrderDto> getAllOrders(){
        User currentUser = authService.getCurrentUser();
        List<Order> orders = orderRepository.getAllByCustomer(currentUser);
        return orders.stream().map(orderMapper::toDto).toList();
    }

    public OrderDto getOrder(Long orderId) {
        Order order = orderRepository
                .getOrderWithItems(orderId)
                .orElseThrow(OrderNotFoundException::new);
        User currentUser = authService.getCurrentUser();
        if (!order.isPlacedBy(currentUser)) {
            throw new AccessDeniedException("You do not have access to this order.");
        }
        return orderMapper.toDto(order);
    }
}
