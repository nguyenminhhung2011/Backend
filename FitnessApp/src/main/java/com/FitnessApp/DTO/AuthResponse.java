package com.FitnessApp.DTO;

import lombok.Getter;

@Getter
public class AuthResponse {
    private final String jwt;
    private final String refreshToken;

    public AuthResponse(String jwt, String refreshToken) {
        this.jwt = jwt;
        this.refreshToken = refreshToken;
    }

}