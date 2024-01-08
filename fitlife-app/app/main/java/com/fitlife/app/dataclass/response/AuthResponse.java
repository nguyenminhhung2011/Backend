package com.fitlife.app.dataclass.response;

import com.fitlife.app.dataclass.dto.user.UserDTO;

public record AuthResponse(String jwt, String refreshToken, UserDTO user) {
}