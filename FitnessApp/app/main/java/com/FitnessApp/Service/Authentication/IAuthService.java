package com.FitnessApp.Service.Authentication;

import com.FitnessApp.DTO.Response.AuthResponse;
import com.FitnessApp.DTO.Request.AuthRequest;
import com.FitnessApp.DTO.Request.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import javax.naming.AuthenticationException;

public interface IAuthService {
    AuthResponse login(AuthRequest request) throws AuthenticationException;
    ResponseEntity<?> register(RegistrationRequest request) throws Exception;
//    void forgotPassword(RecoveryRequest request) throws Exception;
//    void forgotPasswordConfirm(VerificationCodeRequest request);
//    void resetPassword(ResetPasswordRequest request);
    ResponseEntity<?> refreshToken(String refreshToken) ;
    ResponseEntity<?> token(UserDetails request);
}
