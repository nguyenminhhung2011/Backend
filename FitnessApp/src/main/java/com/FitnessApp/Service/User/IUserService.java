package com.FitnessApp.Service.User;

import com.FitnessApp.DTO.DataClass.ActivitiesLog.ActivitiesLogDTO;
import com.FitnessApp.DTO.DataClass.ResponseObject;
import com.FitnessApp.DTO.DataClass.User.UserDTO;
import com.FitnessApp.DTO.DataClass.User.UserProfileDTO;
import com.FitnessApp.DTO.Request.AddActivitiesLogRequest;
import com.FitnessApp.DTO.Request.ChangePasswordRequest;
import com.FitnessApp.DTO.Request.RegistrationRequest;
import com.FitnessApp.Exceptions.AppException.BadRequestException;
import com.FitnessApp.Model.User;

import java.util.List;
public interface IUserService {
    UserDTO getUserById(Long id);
    UserProfileDTO getUserProfile(Long id);

    UserDTO addNewUser(RegistrationRequest user) ;

    List<UserDTO> getAllUser() ;

    UserDTO getUserByUsername(String username);

    ResponseObject changePassword(Long id, ChangePasswordRequest request);

    UserDTO updateUserProfile(Long id,UserDTO userDTO);

    ResponseObject addActivityLog(Long userId,AddActivitiesLogRequest dto) throws BadRequestException;

    ResponseObject addFavoriteExercise(Long userId,Long exeId) throws BadRequestException;

    ResponseObject addWorkoutPlan(Long userId);
}
