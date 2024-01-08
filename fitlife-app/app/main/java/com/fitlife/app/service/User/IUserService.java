package com.fitlife.app.service.User;

import com.fitlife.app.dataclass.dto.ResponseObject;
import com.fitlife.app.dataclass.dto.user.UserDTO;
import com.fitlife.app.dataclass.dto.user.UserProfileDTO;
import com.fitlife.app.dataclass.dto.WorkoutPlanDTO;
import com.fitlife.app.dataclass.request.AddActivitiesLogRequest;
import com.fitlife.app.dataclass.request.ChangePasswordRequest;
import com.fitlife.app.dataclass.request.RegistrationRequest;
import com.fitlife.app.exceptions.AppException.BadRequestException;

import java.util.List;
public interface IUserService {
    UserDTO getUserById(Long id);
    UserProfileDTO getUserProfile(Long id);

    UserDTO addNewUser(RegistrationRequest user) ;

    List<UserDTO> getAllUser() ;

    UserDTO getUserByUsername(String username);

    ResponseObject changePassword(Long id, ChangePasswordRequest request);

    UserDTO updateUserProfile(Long id,UserDTO userDTO);
    WorkoutPlanDTO changeCurrentPlan(Long userId, Long planId);
    WorkoutPlanDTO getCurrentPlan(Long userId);

    ResponseObject addActivityLog(Long userId,AddActivitiesLogRequest dto) throws BadRequestException;

    ResponseObject addFavoriteExercise(Long userId,Long exeId) throws BadRequestException;

    ResponseObject addFavoriteNews(Long userId,Long newsId) throws BadRequestException;

}
