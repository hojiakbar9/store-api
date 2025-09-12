package com.example.store.users;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ChangePasswordRequest {
    @Size(min = 8)
    @NotNull
    private String oldPassword;
    @Size(min = 8)
    @NotNull
    private String newPassword;
}
