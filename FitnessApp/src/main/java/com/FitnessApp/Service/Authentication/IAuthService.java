package com.FitnessApp.Service.Authentication;

import com.FitnessApp.DTO.AuthResponse;
import com.FitnessApp.DTO.AuthRequest;
import com.FitnessApp.DTO.GymerRegistrationRequest;
import com.FitnessApp.Model.User;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAuthService {
    AuthResponse login(AuthRequest request);
    User register(GymerRegistrationRequest request) throws Exception;
//    void forgotPassword(RecoveryRequest request) throws Exception;
//    void forgotPasswordConfirm(VerificationCodeRequest request);
//    void resetPassword(ResetPasswordRequest request);
    AuthResponse refreshToken(String authHeader);
    AuthResponse token(UserDetails request);
}
