package com.fitlife.app.service.Authentication;

import com.fitlife.app.dataclass.response.AuthResponse;
import com.fitlife.app.dataclass.request.AuthRequest;
import com.fitlife.app.dataclass.request.RegistrationRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import javax.naming.AuthenticationException;

public interface IAuthService {
    AuthResponse login(AuthRequest request) throws AuthenticationException;
    ResponseEntity<?> register(RegistrationRequest request) throws Exception;
    ResponseEntity<?> refreshToken(String refreshToken) ;
    ResponseEntity<?> token(UserDetails request);
}
