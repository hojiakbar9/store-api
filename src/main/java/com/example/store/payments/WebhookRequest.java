package com.example.store.payments;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;

import java.util.Map;
@Getter
@Data
@AllArgsConstructor
public class WebhookRequest {
    private Map<String, String> headers;
    private String payload;

}
