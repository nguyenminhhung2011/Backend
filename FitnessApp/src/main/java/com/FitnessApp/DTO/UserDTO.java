package com.FitnessApp.DTO;

import com.FitnessApp.Model.Gymer;
import com.FitnessApp.Model.UserProfile;
import com.FitnessApp.Security.Model.Role;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.HashSet;
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
