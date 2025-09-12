package com.example.store.carts;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class AddItemToCartRequest {
    @NotNull(message = "the id of the product cannot be null")
    private Long productId;
}
