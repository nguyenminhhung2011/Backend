package com.fitlife.app.Service.User;

import com.fitlife.app.DTO.DataClass.ResponseObject;
import com.fitlife.app.DTO.DataClass.User.UserDTO;
import com.fitlife.app.DTO.DataClass.User.UserProfileDTO;
import com.fitlife.app.DTO.Request.AddActivitiesLogRequest;
import com.fitlife.app.DTO.Request.ChangePasswordRequest;
import com.fitlife.app.DTO.Request.RegistrationRequest;
import com.fitlife.app.Exceptions.AppException.BadRequestException;

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

    ResponseObject addFavoriteNews(Long userId,Long newsId) throws BadRequestException;

}
