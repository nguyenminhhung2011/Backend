package com.FitnessApp.Service.User;

import java.util.List;

import com.FitnessApp.DTO.DataClass.ResponseObject;
import com.FitnessApp.DTO.DataClass.User.UserDTO;
import com.FitnessApp.DTO.DataClass.User.UserProfileDTO;
import com.FitnessApp.DTO.Request.RegistrationRequest;
import com.FitnessApp.Exceptions.AppException.BadRequestException;

public interface IUserService {
	UserDTO getUserById(Long id);

	UserProfileDTO getUserProfile(Long id);

	UserDTO addNewUser(RegistrationRequest user);

	List<UserDTO> getAllUser();

	UserDTO getUserByUsername(String username);

	UserDTO updateUserProfile(Long id, UserDTO userDTO);

	ResponseObject addFavoriteExercise(Long userId, Long exeId) throws BadRequestException;

	ResponseObject addWorkoutPlan(Long userId);
}