package com.fitlife.app.service.authentication;

import com.fitlife.app.dataClass.request.AuthRequest;
import com.fitlife.app.dataClass.request.RegistrationRequest;
import com.fitlife.app.dataClass.response.AuthResponse;
import com.fitlife.app.dataClass.response.TokenResponse;
import com.fitlife.app.dataClass.dto.user.UserDTO;
import com.fitlife.app.exceptions.appException.NotFoundException;
import com.fitlife.app.repository.jpa.user.UserRepository;
import com.fitlife.app.utils.mapper.UserMapper;
import com.fitlife.app.model.user.User;
import com.fitlife.app.service.user.IUserService;
import com.fitlife.app.utils.jwt.JwtTokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.Optional;


@Primary
@Service
@AllArgsConstructor
public class AuthServiceImpl implements IAuthService{
    private final UserRepository userRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private final AuthenticationManager authenticationManager;
    private final IUserService userService;
    private final UserMapper userMapper;

    @Override
    public AuthResponse login(AuthRequest request) {
           Optional<User> userOptional = userRepository.findByUsername(request.username());

           if (userOptional.isEmpty()){
               throw new NotFoundException("Can not find user have this username");
           }

           authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                   request.username(),
                   request.password())
           );

           String jwt = jwtTokenUtils.generateToken(request.username());
           String freshToken = jwtTokenUtils.generateTokenRefresh(request.username());

           final User user = userOptional.get();

           user.setRefreshToken(freshToken);

           final User currentUser  = userRepository.save(user);
           final UserDTO userDTO = userMapper.userDTO(currentUser);
           return new AuthResponse(jwt,freshToken,userDTO);
    }

    @Override
    public ResponseEntity<?> register(RegistrationRequest request) throws Exception{
           try{
               Optional<User> userExits = userRepository.findByUsername(request.getUsername());

               if (userExits.isPresent()) {
                   throw new Exception("Username is existed");
               }

               UserDTO userDTO = userService.addNewUser(request);

               return ResponseEntity.ok(userDTO);

           }catch (Exception e){
               throw new AuthenticationException("Failed: Can not register user: " + e.getMessage());
           }
    }

    @Override
    public ResponseEntity<?> refreshToken(String token) {
        try {
            if (token == null || !token.startsWith("Bearer ")) {
                throw new Exception("Invalid refresh token");
            }
            final String refreshToken = token.substring("Bearer ".length());

            jwtTokenUtils.validateToken(refreshToken);

            final String username = jwtTokenUtils.getUsernameFromToken(refreshToken);
            Optional<User> user = userRepository.findByUsername(username);

            if (user.isEmpty()){
                throw new NotFoundException("Can not found corresponding user");
            }

            final String jwt = jwtTokenUtils.generateToken(user.get().getUsername());

            return ResponseEntity.ok(new TokenResponse(jwt,refreshToken));

        }
        catch (Exception e) {
           throw new BadCredentialsException("Refresh token failed: " + e.getMessage());
        }
    }

    @Override
    public ResponseEntity<?> token(UserDetails request) {
        try {
            Optional<User> findUser = userRepository.findByUsername(request.getUsername());
            if (findUser.isEmpty()){
                throw new NotFoundException("Can not found corresponding user");
            }

            String accessToken = jwtTokenUtils.generateToken(request.getUsername());
            String refreshToken = jwtTokenUtils.generateTokenRefresh(request.getUsername());

            return ResponseEntity.ok(new TokenResponse(accessToken, refreshToken));
        }catch (Exception e){
            throw new BadCredentialsException("Create token failed: " + e.getMessage());
        }
    }
}
