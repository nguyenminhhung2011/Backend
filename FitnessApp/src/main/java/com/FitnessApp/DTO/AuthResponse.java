package com.FitnessApp.DTO;

import com.FitnessApp.DTO.User.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponse {
    private final String jwt;
    private final String refreshToken;
    private final UserDTO user;
}