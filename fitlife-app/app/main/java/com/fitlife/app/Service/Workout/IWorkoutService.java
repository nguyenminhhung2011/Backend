package com.fitlife.app.Service.Workout;

import com.fitlife.app.DTO.Request.DailyWorkoutReq;
import com.fitlife.app.DTO.Request.WorkoutPlanReq;
import com.fitlife.app.DTO.Response.WorkoutPlanResponse;
import com.fitlife.app.Exceptions.AppException.BadRequestException;
import com.fitlife.app.Model.DailyWorkout;
import com.fitlife.app.Model.WorkoutPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface IWorkoutService {
    void delete(Long id);

    WorkoutPlanResponse createDailyPlan(DailyWorkoutReq workoutPlanDTO, Long id) throws BadRequestException;

    WorkoutPlanResponse createWorkoutPlan( WorkoutPlanReq workoutPlanDTO,Long idUser ) throws  BadRequestException;

    List<WorkoutPlanResponse> getAll() ;
    List<WorkoutPlanResponse> getMyWorkoutPlan(Long idUser);

    List<WorkoutPlan> getWorkoutPlansByUserProfileId(Long userProfileId);
    List<DailyWorkout>  getAllDailyPlan(String id) throws BadRequestException;

    Page<Object> searchWorkoutPlans(Long id, String name, Date startDate, Date endDate, Pageable pageable);
}
