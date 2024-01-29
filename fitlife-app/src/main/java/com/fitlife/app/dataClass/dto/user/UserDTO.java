package com.fitlife.app.dataClass.dto.user;

import com.fitlife.app.dataClass.dto.trainer.TrainerDto;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDTO {
    private Long id;
    private String username;
    private String refreshToken;
//    private Set<Role> roles;
    private List<TrainerDto> trainers;
    private UserProfileDTO userProfile;
}
