package com.fitlife.app.service.authentication;

import com.fitlife.app.dataClass.response.AuthResponse;
import com.fitlife.app.dataClass.request.AuthRequest;
import com.fitlife.app.dataClass.request.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import javax.naming.AuthenticationException;

public interface IAuthService {
    AuthResponse login(AuthRequest request) throws AuthenticationException;
    ResponseEntity<?> register(RegistrationRequest request) throws Exception;
    ResponseEntity<?> refreshToken(String refreshToken) ;
    ResponseEntity<?> token(UserDetails request);
}
