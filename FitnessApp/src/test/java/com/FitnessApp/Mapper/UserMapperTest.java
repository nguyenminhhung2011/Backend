package com.FitnessApp.Mapper;

import com.FitnessApp.DTO.DataClass.User.UserDTO;
import com.FitnessApp.DTO.DataClass.User.UserProfileDTO;
import com.FitnessApp.Enums.Frequency;
import com.FitnessApp.Enums.Gender;
import com.FitnessApp.Enums.ThemeStatus;
import com.FitnessApp.Model.User;
import com.FitnessApp.Model.UserProfile;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;
    UserProfile userProfile;
    User user;
    @BeforeEach
    void setUp() {
        userProfile = new UserProfile(2L,"", Timestamp.from(Instant.now()),"","", Gender.OTHER, 1.0,1.0,ThemeStatus.DARK, Frequency.MUCH,null,new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        user = new User(0L,"","","",userProfile);
    }

    @Test
    void setUserProfile() {
      final UserProfileDTO userProfileDTO = userMapper.userProfileDTO(userProfile);
    System.out.println(userProfileDTO.getProfileId());
}

    @Test
    void setUserDTO() {
      final UserDTO userDTO = userMapper.userDTO(user);
    }
}