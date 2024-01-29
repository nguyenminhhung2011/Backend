package com.fitlife.app.dataClass.response;

import com.fitlife.app.dataClass.dto.user.UserDTO;

public record AuthResponse(String jwt, String refreshToken, UserDTO user) {
}