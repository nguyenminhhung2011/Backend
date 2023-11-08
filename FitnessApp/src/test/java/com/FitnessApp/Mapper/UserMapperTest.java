package com.FitnessApp.Mapper;

import com.FitnessApp.DTO.User.UserDTO;
import com.FitnessApp.Enums.Frequency;
import com.FitnessApp.Enums.Gender;
import com.FitnessApp.Enums.ThemeStatus;
import com.FitnessApp.Model.User;
import com.FitnessApp.Model.UserProfile;
import com.cloudinary.provisioning.Account;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
class UserMapperTest {

    @Autowired
    UserMapper userMapper;
    UserProfile userProfile;
    User user;
    @BeforeEach
    void setUp() {
//        userProfile = new UserProfile(0L,1.0,1.0,"","","", ThemeStatus.DARK, Gender.OTHER, Frequency.MUCH,null,new ArrayList<>(),new ArrayList<>(),new ArrayList<>());
        user = new User(0L,"","","",userProfile);
    }

    @Test
    void setUserProfile() {
        userMapper.userProfileDTO(userProfile);
    }

    @Test
    void setUserDTO() {
      final UserDTO userDTO = userMapper.userDTO(user);
    }
}