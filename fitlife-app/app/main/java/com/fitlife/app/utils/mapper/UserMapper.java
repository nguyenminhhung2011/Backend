package com.fitlife.app.utils.mapper;

import com.fitlife.app.dataclass.dto.user.UserDTO;
import com.fitlife.app.dataclass.dto.user.UserProfileDTO;
import com.fitlife.app.model.User.User;
import com.fitlife.app.model.User.UserProfile;
import lombok.AllArgsConstructor;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;
    public UserDTO userDTO(User user){
        TypeMap<User,UserDTO> propertyMapper = modelMapper.typeMap(User.class,UserDTO.class);
        Converter<UserProfile,UserProfileDTO> userProfileConverter = mappingContext -> userProfileDTO(mappingContext.getSource());
        return propertyMapper.addMappings(mapper-> mapper.using(userProfileConverter)).map(user);
    }

    public UserProfileDTO userProfileDTO(UserProfile entity){
        TypeMap<UserProfile,UserProfileDTO> typeMap = modelMapper.typeMap(UserProfile.class,UserProfileDTO.class);
        return typeMap.map(entity);
    }
}
