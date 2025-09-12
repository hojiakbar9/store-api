package com.example.store.carts;

import com.example.store.products.Product;
import com.example.store.products.ProductNotFoundException;
import com.example.store.products.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class CartService {
    private  CartMapper cartMapper;
    private CartRepository cartRepository;
    private ProductRepository productRepository;

    public CartDto createCart(){
        var cart = new Cart();
        cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartItemDto addToCart(UUID cartId, Long productId)  {

        var cart = cartRepository.getCartWithItems(cartId).orElse(null);

        if(cart == null)
            throw new CartNotFoundException();

        Product product = productRepository.findById(productId).orElse(null);

        if(product == null)
            throw new ProductNotFoundException();

        var cartItem  = cart.addItem(product);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }
    public CartDto getCart(UUID cartId){
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null)
            throw new CartNotFoundException();

        return cartMapper.toDto(cart);
    }
    public CartItemDto updateCartItem(UUID cartId, Long productId, Integer quantity){
        var cart = cartRepository.getCartWithItems(cartId).orElse(null);

        if(cart == null)
            throw new CartNotFoundException();

        CartItem cartItem = cart.getItem(productId);
        if(cartItem == null)
            throw new ProductNotFoundException();
        cartItem.setQuantity(quantity);
        cartRepository.save(cart);
        return cartMapper.toDto(cartItem);
    }
    public void deleteProduct(UUID cartId, Long productId){
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);

        if(cart == null) {
            throw new CartNotFoundException();
        }
        cart.removeItem(productId);
        cartRepository.save(cart);
    }
    public void clearCart(UUID cartId){
        Cart cart = cartRepository.getCartWithItems(cartId).orElse(null);
        if(cart == null)
            throw new CartNotFoundException();
        cart.clear();
        cartRepository.save(cart);
    }
}
