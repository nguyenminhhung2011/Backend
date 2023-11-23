package com.FitnessApp.Controller;

import com.FitnessApp.DTO.DataClass.User.UserDTO;
import com.FitnessApp.DTO.Request.AddActivitiesLogRequest;
import com.FitnessApp.DTO.Request.ChangePasswordRequest;
import com.FitnessApp.Exceptions.AppException.BadRequestException;
import com.FitnessApp.Security.Model.CurrentUser;
import com.FitnessApp.Security.Model.FitLifeUserDetail;
import com.FitnessApp.Service.User.IUserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@AllArgsConstructor
public class UserController {
    private final IUserService uService;

    @GetMapping("/{id}")
    ResponseEntity<?> getUser(@PathVariable long id){
        return ResponseEntity.ok().body(uService.getUserById(id));
    }

    @GetMapping("/profile/{id}")
    ResponseEntity<?> getUserProfile(@PathVariable long id){
        return ResponseEntity.ok(uService.getUserProfile(id));
    }

    @GetMapping("/name/{username}")
    ResponseEntity<?> getUserByUserName(@PathVariable String username){
        return ResponseEntity.ok(uService.getUserByUsername(username));
    }
    @GetMapping
    ResponseEntity<?> getAllUser(){
        return ResponseEntity.ok(uService.getAllUser());
    }

    @GetMapping("/me")
    public ResponseEntity<?> profile(@CurrentUser FitLifeUserDetail flUserDetail) {
        UserDTO user = uService.getUserById(flUserDetail.getId());
        return ResponseEntity.ok(user);
    }

    @PatchMapping("/change-password")
    public ResponseEntity<?> changePassword(@CurrentUser FitLifeUserDetail ctx, @Valid @RequestBody ChangePasswordRequest request){
        return ResponseEntity.ok(uService.changePassword(ctx.getId(),request));
    }

    @PutMapping("/update-profile")
    public ResponseEntity<?> updateProfile(@CurrentUser FitLifeUserDetail ctx, @Valid @RequestBody UserDTO userDTO){
        return ResponseEntity.ok(uService.updateUserProfile(ctx.getId(),userDTO));
    }

    @PutMapping("/exercise-favorite/{id}")
    public ResponseEntity<?> addFavoriteExercise(@CurrentUser FitLifeUserDetail ctx, @PathVariable Long id) throws BadRequestException {
        final var responseObject = uService.addFavoriteExercise(ctx.getId(),id);
        return new ResponseEntity<>(responseObject.message(), HttpStatusCode.valueOf(responseObject.status()));
    }

    @PostMapping("/activity-log")
   public ResponseEntity<?> addActivityLog(
           @CurrentUser FitLifeUserDetail ctx,
           @Valid @RequestBody AddActivitiesLogRequest request
    ) throws BadRequestException {
        final var responseObject = uService.addActivityLog(ctx.getId(),request);
        return new ResponseEntity<>(responseObject.message(), HttpStatusCode.valueOf(responseObject.status()));
    }
}
