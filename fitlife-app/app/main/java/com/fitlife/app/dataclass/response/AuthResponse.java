package com.fitlife.app.DTO.Response;

import com.fitlife.app.DTO.DataClass.User.UserDTO;

public record AuthResponse(String jwt, String refreshToken, UserDTO user) {
}