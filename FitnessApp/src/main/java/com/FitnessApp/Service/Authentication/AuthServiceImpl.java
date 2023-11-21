package com.FitnessApp.Service.Authentication;

import com.FitnessApp.DTO.DataClass.ResponseObject;
import com.FitnessApp.DTO.Request.AuthRequest;
import com.FitnessApp.DTO.Request.PageRequest;
import com.FitnessApp.DTO.Request.RegistrationRequest;
import com.FitnessApp.DTO.Response.AuthResponse;
import com.FitnessApp.DTO.Response.TokenResponse;
import com.FitnessApp.DTO.DataClass.User.UserDTO;
import com.FitnessApp.Exceptions.AppException.BadRequestException;
import com.FitnessApp.Exceptions.AppException.NotFoundException;
import com.FitnessApp.Mapper.UserMapper;
import com.FitnessApp.Model.User;
import com.FitnessApp.Model.UserProfile;
import com.FitnessApp.Repository.ExerciseRepository;
import com.FitnessApp.Repository.UserProfileRepository;
import com.FitnessApp.Repository.UserRepository;
import com.FitnessApp.Service.ExcerciseService.ExerciseService;
import com.FitnessApp.Service.User.IUserService;
import com.FitnessApp.Utils.JwtTokenUtils;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.orm.hibernate5.SessionHolder;
import org.springframework.orm.hibernate5.SpringJtaSessionContext;
import org.springframework.orm.hibernate5.SpringSessionContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.naming.AuthenticationException;
import java.util.List;
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
