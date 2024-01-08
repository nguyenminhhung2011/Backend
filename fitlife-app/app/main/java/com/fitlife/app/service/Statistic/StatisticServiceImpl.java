package com.fitlife.app.service.Statistic;

import com.fitlife.app.repository.ActivitiesLogRepository;
import com.fitlife.app.repository.DailyWorkoutRepository;
import com.fitlife.app.repository.Exercise.ExerciseRepository;
import com.fitlife.app.repository.User.UserProfileRepository;
import com.fitlife.app.repository.WorkoutRepository;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class StatisticServiceImpl implements IStatisticService  {
    private final UserProfileRepository profileRepository;
    private final WorkoutRepository workoutRepository;
    private final ExerciseRepository exerciseRepository;
    private final DailyWorkoutRepository dailyWorkoutRepository;
    private final ActivitiesLogRepository activitiesLogRepository;
}
