package com.FitnessApp.DTO.Response;

import lombok.Data;
import lombok.EqualsAndHashCode;

public record TokenResponse(String jwt, String refreshToken) {
}
