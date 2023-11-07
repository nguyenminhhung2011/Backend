package com.FitnessApp.DTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

public record TokenResponse(String jwt, String refreshToken) {
}
