package com.fitlife.app.dataClass.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChangePasswordRequest {
    private String oldPassword;

    @Size(min = 8, message = "password must be minimum 8 characters")
    @NotBlank(message = "new password is required")
    private String newPassword;

    @Size(min = 8, message = "password must be minimum 8 characters")
    @NotBlank(message = "confirm password is required")
    private String confirmPassword;
}

