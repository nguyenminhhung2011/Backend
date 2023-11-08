package com.FitnessApp.DTO.Response;

import com.FitnessApp.DTO.User.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public record AuthResponse(String jwt, String refreshToken, UserDTO user) {
}