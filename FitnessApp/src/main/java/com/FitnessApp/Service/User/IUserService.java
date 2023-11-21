package com.FitnessApp.Service.User;

import com.FitnessApp.DTO.DataClass.User.UserDTO;
import com.FitnessApp.DTO.DataClass.User.UserProfileDTO;
import com.FitnessApp.DTO.Request.RegistrationRequest;
import com.FitnessApp.Model.User;

import java.util.List;
public interface IUserService {
    UserDTO getUserById(Long id);
    UserProfileDTO getUserProfile(Long id);

    UserDTO addNewUser(RegistrationRequest user) ;

    List<UserDTO> getAllUser() ;
}
