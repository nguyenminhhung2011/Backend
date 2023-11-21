package com.FitnessApp.DTO.Response;

import com.FitnessApp.DTO.DataClass.User.UserDTO;

public record AuthResponse(String jwt, String refreshToken, UserDTO user) {
}