package com.fitlife.app.Service.Workout;

import com.fitlife.app.DTO.Request.DailyWorkoutReq;
import com.fitlife.app.DTO.Request.WorkoutPlanReq;
import com.fitlife.app.DTO.Response.WorkoutPlanResponse;
import com.fitlife.app.Exceptions.AppException.BadRequestException;
import com.fitlife.app.Model.DailyWorkout;
import com.fitlife.app.Model.WorkoutPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;

public interface IWorkoutService {
    void delete(Long id);

    WorkoutPlan createDailyPlan(DailyWorkoutReq workoutPlanDTO, Long id) throws BadRequestException;

    WorkoutPlan createWorkoutPlan( WorkoutPlanReq workoutPlanDTO,Long idUser ) throws  BadRequestException;
    WorkoutPlan findById(Long idUser);

    List<WorkoutPlanResponse> getAll() ;
    List<WorkoutPlanResponse> getMyWorkoutPlan(Long idUser);

    List<WorkoutPlan> getWorkoutPlansByUserProfileId(Long userProfileId);
    List<DailyWorkout>  getAllDailyPlan(String id) throws BadRequestException;

    Page<Object> searchWorkoutPlans(Long id, String name, Date startDate, Date endDate, Pageable pageable);
}
