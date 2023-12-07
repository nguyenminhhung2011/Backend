package com.fitlife.Mapper;

import com.fitlife.app.DTO.DataClass.User.UserDTO;
import com.fitlife.app.Model.User.User;
import com.fitlife.app.Model.User.UserProfile;
import com.fitlife.app.Utils.Mapper.UserMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

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