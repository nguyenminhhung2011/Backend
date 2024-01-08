package com.fitlife.app.dataclass.dto.user;

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
