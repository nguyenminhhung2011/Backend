package com.FitnessApp.DTO.Response;

import com.FitnessApp.DTO.User.UserDTO;

public record AuthResponse(String jwt, String refreshToken, UserDTO user) {
}