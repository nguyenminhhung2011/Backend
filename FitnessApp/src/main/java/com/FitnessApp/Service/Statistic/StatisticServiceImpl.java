package com.FitnessApp.Service.Statistic;

import com.FitnessApp.Repository.ActivitiesLogRepository;
import com.FitnessApp.Repository.DailyWorkoutRepository;
import com.FitnessApp.Repository.Exercise.ExerciseRepository;
import com.FitnessApp.Repository.User.UserProfileRepository;
import com.FitnessApp.Repository.WorkoutRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StatisticServiceImpl implements IStatisticService  {
    private final UserProfileRepository profileRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final DailyWorkoutRepository dailyWorkoutRepository;
    private final ActivitiesLogRepository activitiesLogRepository;
}
