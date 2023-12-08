package com.fitlife.app.Service.Workout;

import com.fitlife.app.DTO.Request.DailyWorkoutReq;
import com.fitlife.app.DTO.Request.WorkoutPlanReq;
import com.fitlife.app.DTO.Response.WorkoutPlanResponse;
import com.fitlife.app.Exceptions.AppException.BadRequestException;
import com.fitlife.app.Model.WorkoutPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Date;
import java.util.List;

public interface IWorkoutService {
    WorkoutPlan createDailyPlan(DailyWorkoutReq workoutPlanDTO, Long id) throws BadRequestException;

    WorkoutPlan createWorkoutPlan(@RequestBody WorkoutPlanReq workoutPlanDTO,Long idUser ) throws  BadRequestException;

    WorkoutPlan findById(Long idUser);
    void delete(Long id);

    List<WorkoutPlanResponse> getAll() ;
    List<WorkoutPlanResponse> getMyWorkoutPlan(Long idUser);

    List<WorkoutPlan> getWorkoutPlansByUserProfileId(Long userProfileId);
    Page<Object> searchWorkoutPlans(Long id, String name, Date startDate, Date endDate, Pageable pageable);
}
