package com.FitnessApp.Mapper;

import com.FitnessApp.DTO.DataClass.User.UserDTO;
import com.FitnessApp.DTO.DataClass.User.UserProfileDTO;
import com.FitnessApp.Model.User;
import com.FitnessApp.Model.UserProfile;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;
    public UserDTO userDTO(User user){
        TypeMap<User,UserDTO> propertyMapper = modelMapper.typeMap(User.class,UserDTO.class);
        return propertyMapper.map(user);
    }

    public UserProfileDTO userProfileDTO(UserProfile entity){
        TypeMap<UserProfile,UserProfileDTO> typeMap = modelMapper.typeMap(UserProfile.class,UserProfileDTO.class);
        return typeMap
                .addMappings(mapper -> mapper.map(UserProfile::getId,UserProfileDTO::setProfileId))
                .map(entity);
    }
}
