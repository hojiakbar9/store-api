package com.example.store.payments;

import com.example.store.carts.Cart;
import com.example.store.orders.Order;
import com.example.store.carts.CartEmptyException;
import com.example.store.carts.CartNotFoundException;
import com.example.store.carts.CartRepository;
import com.example.store.orders.OrderRepository;
import com.example.store.auth.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CheckoutService {
    private final CartRepository cartRepository;
    private final OrderRepository orderRepository;
    private final AuthService authService;
    private final PaymentGateway paymentGateway;

    @Transactional
    public CheckoutResponse checkout(UUID cartId){
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null)
            throw new CartNotFoundException();

        if(cart.isEmpty())
            throw new CartEmptyException();

        Order order = Order.fromCart(cart, authService.getCurrentUser());
        orderRepository.save(order);

        try{
            CheckoutSession checkoutSession = paymentGateway.createCheckoutSession(order);
            return new CheckoutResponse(order.getId(), checkoutSession.getCheckoutUrl());
        }
        catch (PaymentException exception){
            orderRepository.delete(order);
            throw exception;
        }
    }
    public void handleWebhookRequest(WebhookRequest request){
        paymentGateway.parseWebhookRequest(request).ifPresent(
                paymentResult -> {
                    var order = orderRepository.findById(paymentResult.getOrderId()).orElseThrow();
                    order.setStatus(paymentResult.getStatus());
                    orderRepository.save(order);
                }
        );
    }
}
