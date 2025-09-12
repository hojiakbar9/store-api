package com.example.store.carts;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CartItemDto {
    private CartProductDto product;
    private Integer quantity;;
    private BigDecimal totalPrice;

}
