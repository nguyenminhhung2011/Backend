package com.FitnessApp.DTO;

import com.FitnessApp.Model.UserProfile;
import com.FitnessApp.Model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class UserDTO {
    private String username;
    private String password;
    private Boolean isEnable;
    private String refreshToken;
    private Set<Role> roles;
    private UserProfile userProfile;
}
