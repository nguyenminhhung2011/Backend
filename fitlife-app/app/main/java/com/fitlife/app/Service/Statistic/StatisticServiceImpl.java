package com.fitlife.app.Service.Statistic;

import com.fitlife.app.Repository.ActivitiesLogRepository;
import com.fitlife.app.Repository.DailyWorkoutRepository;
import com.fitlife.app.Repository.Exercise.ExerciseRepository;
import com.fitlife.app.Repository.User.UserProfileRepository;
import com.fitlife.app.Repository.WorkoutRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StatisticServiceImpl implements IStatisticService  {
    private final UserProfileRepository profileRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final DailyWorkoutRepository dailyWorkoutRepository;
    private final ActivitiesLogRepository activitiesLogRepository;
}
