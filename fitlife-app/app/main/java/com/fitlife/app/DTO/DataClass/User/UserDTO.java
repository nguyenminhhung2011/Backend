package com.fitlife.app.DTO.DataClass.User;

import lombok.*;

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
