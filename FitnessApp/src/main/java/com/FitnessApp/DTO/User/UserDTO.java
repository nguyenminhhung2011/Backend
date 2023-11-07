package com.FitnessApp.DTO.User;

import com.FitnessApp.Model.Role;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String refreshToken;
//    private Set<Role> roles;
    private UserProfileDTO userProfile;
}
