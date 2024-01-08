package com.fitlife.app.Service.Workout;

import com.fitlife.app.DTO.DataClass.DailyWorkoutDTO;
import com.fitlife.app.DTO.Request.DailyWorkoutRequest;
import com.fitlife.app.DTO.Request.GetChartRequest;
import com.fitlife.app.DTO.Request.WorkoutPlanRequest;
import com.fitlife.app.DTO.Response.ChartResponse;
import com.fitlife.app.DTO.Response.FitOverviewResponse;
import com.fitlife.app.DTO.Response.WorkoutPlanResponse;
import com.fitlife.app.Exceptions.AppException.BadRequestException;
import com.fitlife.app.Model.Workout.WorkoutPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Date;
import java.util.List;

public interface IWorkoutService {
    void delete(Long id);

    DailyWorkoutDTO createDailyPlan(DailyWorkoutRequest workoutPlanDTO, Long id) throws BadRequestException;

    WorkoutPlanResponse createWorkoutPlan(WorkoutPlanRequest workoutPlanDTO, Long idUser ) throws  BadRequestException;

    List<WorkoutPlanResponse> getAll() ;
    List<WorkoutPlanResponse> getMyWorkoutPlan(Long idUser);
    List<WorkoutPlanResponse> getActiveWorkoutPlan(Long time) throws BadRequestException;

    List<WorkoutPlan> getWorkoutPlansByUserProfileId(Long userProfileId);
    List<DailyWorkoutDTO>  getAllDailyPlan(String id) throws BadRequestException;
    FitOverviewResponse getChartView(GetChartRequest request, Long userId) ;

    Page<Object> searchWorkoutPlans(Long id, String name, Long startDate, Long endDate, Pageable pageable);
}
