package com.example.store.carts;

import com.example.store.products.Product;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "carts", schema = "store_api")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private UUID id;

    @Column(name = "date_created", insertable = false, updatable = false) //ignore this field when generating sql statements
    private LocalDate dateCreated;

    @OneToMany(mappedBy = "cart", cascade = CascadeType.MERGE, orphanRemoval = true, fetch = FetchType.EAGER)
    private Set<CartItem> items = new LinkedHashSet<>();

    public BigDecimal getTotalPrice(){
        return items.stream()
                .map(CartItem::getTotalPrice)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    public CartItem getItem(Long productId){
        return items.stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst()
                .orElse(null);
    }

    public CartItem addItem(Product product){
        CartItem cartItem = getItem(product.getId());
        if(cartItem != null)
            cartItem.setQuantity(cartItem.getQuantity() + 1);
        else{
            cartItem = new CartItem();
            cartItem.setProduct(product);
            cartItem.setCart(this);
            cartItem.setQuantity(1);
            items.add(cartItem);
        }
        return cartItem;
    }
    public void removeItem(Long productId){
        CartItem item = getItem(productId);
        if(item != null){
            items.remove(item);
            item.setCart(null);
        }
    }

    public void clear() {
        items.forEach(item -> item.setCart(null));
        items.clear();
    }
    public boolean isEmpty(){
        return items.isEmpty();
    }

}