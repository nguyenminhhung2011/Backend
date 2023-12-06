package com.FitnessApp.Mapper;

import com.FitnessApp.DTO.DataClass.User.UserDTO;
import com.FitnessApp.DTO.DataClass.User.UserProfileDTO;
import com.FitnessApp.Utils.Enums.Frequency;
import com.FitnessApp.Utils.Enums.Gender;
import com.FitnessApp.Utils.Enums.ThemeStatus;
import com.FitnessApp.Model.User.User;
import com.FitnessApp.Model.User.UserProfile;
import com.FitnessApp.Utils.Mapper.UserMapper;
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
        user = new User(0L,"","","",userProfile);
    }

    @Test
    void setUserProfile() {
}

    @Test
    void setUserDTO() {
      final UserDTO userDTO = userMapper.userDTO(user);
    }
}