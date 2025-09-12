package com.example.store.common;

import lombok.Data;

@Data
public class ErrorDto {
    private String error;

    public ErrorDto(String error) {
        this.error = error;
    }
}
