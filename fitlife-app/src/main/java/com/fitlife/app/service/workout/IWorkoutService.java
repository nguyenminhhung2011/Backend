package com.fitlife.app.service.workout;

import com.fitlife.app.dataClass.dto.DailyWorkoutDTO;
import com.fitlife.app.dataClass.request.DailyWorkoutRequest;
import com.fitlife.app.dataClass.request.GetChartRequest;
import com.fitlife.app.dataClass.request.WorkoutPlanRequest;
import com.fitlife.app.dataClass.response.FitOverviewResponse;
import com.fitlife.app.dataClass.response.WorkoutPlanResponse;
import com.fitlife.app.exceptions.appException.BadRequestException;
import com.fitlife.app.model.workout.WorkoutPlan;
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
