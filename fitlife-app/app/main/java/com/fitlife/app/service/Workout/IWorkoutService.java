package com.fitlife.app.service.Workout;

import com.fitlife.app.dataclass.dto.DailyWorkoutDTO;
import com.fitlife.app.dataclass.request.DailyWorkoutRequest;
import com.fitlife.app.dataclass.request.GetChartRequest;
import com.fitlife.app.dataclass.request.WorkoutPlanRequest;
import com.fitlife.app.dataclass.response.FitOverviewResponse;
import com.fitlife.app.dataclass.response.WorkoutPlanResponse;
import com.fitlife.app.exceptions.AppException.BadRequestException;
import com.fitlife.app.model.Workout.WorkoutPlan;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

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
