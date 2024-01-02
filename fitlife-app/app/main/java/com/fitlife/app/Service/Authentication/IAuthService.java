package com.fitlife.app.Service.Authentication;

import com.fitlife.app.DTO.Response.AuthResponse;
import com.fitlife.app.DTO.Request.AuthRequest;
import com.fitlife.app.DTO.Request.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import javax.naming.AuthenticationException;

public interface IAuthService {
    AuthResponse login(AuthRequest request) throws AuthenticationException;
    ResponseEntity<?> register(RegistrationRequest request) throws Exception;
    ResponseEntity<?> refreshToken(String refreshToken) ;
    ResponseEntity<?> token(UserDetails request);
}
