package com.FitnessApp.Service.Authentication;

import com.FitnessApp.DTO.AuthResponse;
import com.FitnessApp.DTO.AuthRequest;
import com.FitnessApp.DTO.GymerRegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthService {
    AuthResponse login(AuthRequest request);
    ResponseEntity<?> register(GymerRegistrationRequest request) throws Exception;
//    void forgotPassword(RecoveryRequest request) throws Exception;
//    void forgotPasswordConfirm(VerificationCodeRequest request);
//    void resetPassword(ResetPasswordRequest request);
    ResponseEntity<?> refreshToken(String refreshToken);
    ResponseEntity<?> token(UserDetails request);
}
