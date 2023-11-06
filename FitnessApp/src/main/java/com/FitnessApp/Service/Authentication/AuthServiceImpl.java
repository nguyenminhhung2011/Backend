package com.FitnessApp.Service.Authentication;

import com.FitnessApp.DTO.AuthRequest;
import com.FitnessApp.DTO.AuthResponse;
import com.FitnessApp.DTO.GymerRegistrationRequest;
import com.FitnessApp.DTO.ResponseObject;
import com.FitnessApp.Exceptions.NotFoundException;
import com.FitnessApp.Model.Role;
import com.FitnessApp.Model.User;
import com.FitnessApp.Repository.RoleRepository;
import com.FitnessApp.Repository.UserRepository;
import com.FitnessApp.Utils.JwtTokenUtils;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;


@Primary
@Service
@AllArgsConstructor
public class AuthServiceImpl implements IAuthService{
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtTokenUtils jwtTokenUtils;
    private AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;


    @Override
    public AuthResponse login(AuthRequest request) {
        List<User> findByUsername = userRepository.findByUsername(request.username());

        if (findByUsername.isEmpty()){
            throw new NotFoundException("Can not find user have this username");
        }
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                ));

        String jwt = jwtTokenUtils.generateToken(request.username());
        String freshToken = jwtTokenUtils.generateTokenRefresh(request.username());

        final User user = findByUsername.get(0);
//        Set<Role> roles = user.getRoles();
//        boolean check = false;
//
//        for (Role role : roles) {
//            if (Objects.equals(role.getNameRole(), "CLIENT")) {
//                check = true;
//                break;
//            }
//        }
//
//        if (!check) {
//            throw new ;
//        }

        user.setRefreshToken(freshToken);

        final User currentUser  = userRepository.save(user);

        return new AuthResponse(jwt,freshToken);
    }

    @Override
    public User register(GymerRegistrationRequest request) throws Exception {
        return null;
    }

    @Override
    public AuthResponse refreshToken(String authHeader) {
        return null;
    }

    @Override
    public AuthResponse token(UserDetails request) {
        return null;
    }
}
