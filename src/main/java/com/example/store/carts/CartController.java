package com.example.store.carts;

import com.example.store.common.ErrorDto;
import com.example.store.products.ProductNotFoundException;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/carts")
public class CartController {
    private final CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> createCart(
            UriComponentsBuilder uriBuilder
    ){
        var cartDto = cartService.createCart();
        URI uri = uriBuilder.path("carts/{id}").buildAndExpand(cartDto.getId()).toUri();
        return ResponseEntity.created(uri).body(cartDto);
    }
    @PostMapping("/{cartId}/items")
    public ResponseEntity<CartItemDto> addToCart(
            @PathVariable UUID cartId,
            @Valid @RequestBody AddItemToCartRequest request){
        var cartItemDTO = cartService.addToCart(cartId, request.getProductId());
        return ResponseEntity.status(HttpStatus.CREATED).body(cartItemDTO);
    }

    @GetMapping("/{id}")
    public CartDto getCart(@PathVariable UUID id){
        return  cartService.getCart(id);
    }
    @PutMapping("/{cartId}/items/{productId}")
    public CartItemDto updateCartItem(
            @PathVariable UUID cartId,
            @PathVariable Long productId,
           @Valid @RequestBody UpdateCartItemRequest request ){

        return cartService.updateCartItem(cartId, productId, request.getQuantity());
    }
    @DeleteMapping("/{cartId}/items/{productId}")
    public ResponseEntity<?> deleteProduct(
            @PathVariable UUID cartId,
            @PathVariable Long productId)
    {
        cartService.deleteProduct(cartId, productId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/{cartId}/items")
    public ResponseEntity<?> clearCart(@PathVariable UUID cartId){
        cartService.clearCart(cartId);
        return ResponseEntity.noContent().build();
    }

    @ExceptionHandler(CartNotFoundException.class)
    public ResponseEntity<ErrorDto> handleCartNotFound(){
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ErrorDto("Cart not found"));
    }
    @ExceptionHandler(ProductNotFoundException.class)
    public ResponseEntity<ErrorDto> handleProductNotFound(){
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorDto("Product not found"));
    }

}
