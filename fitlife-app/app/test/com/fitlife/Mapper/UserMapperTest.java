package com.fitlife.Mapper;

import com.fitlife.app.dataClass.dto.user.UserDTO;
import com.fitlife.app.model.user.User;
import com.fitlife.app.model.user.UserProfile;
import com.fitlife.app.utils.mapper.UserMapper;
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
        user = new User(0L,"","","",userProfile,null,null);
    }

    @Test
    void setUserProfile() {
}

    @Test
    void setUserDTO() {
      final UserDTO userDTO = userMapper.userDTO(user);
    }
}