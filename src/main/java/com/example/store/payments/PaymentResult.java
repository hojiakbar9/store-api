package com.example.store.payments;

import com.example.store.orders.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

@Data
@Getter
@AllArgsConstructor
public class PaymentResult {
   private Long orderId;
   private PaymentStatus status;
}
