package com.FitnessApp.DTO;
public class AuthResponse {
    private String jwt;
    private String refreshToken;

    public AuthResponse(String jwt, String refreshToken) {
        this.jwt = jwt;
        this.refreshToken = refreshToken;
    }

    public String getJwt() {
        return jwt;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}