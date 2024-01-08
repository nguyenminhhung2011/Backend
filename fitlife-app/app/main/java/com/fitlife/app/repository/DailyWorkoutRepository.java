package com.fitlife.app.repository;

import com.fitlife.app.model.Workout.DailyWorkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DailyWorkoutRepository extends JpaRepository<DailyWorkout, Long> {
    @Query(value = "SELECT DP.* FROM daily_workout AS DP " +
            "JOIN workout_plan AS P ON DP.workout_plan_id = P.id " +
            "WHERE P.user_profile_id = :userId AND DP.time >= :startDate AND DP.time <= :endDate", nativeQuery = true)
    List<DailyWorkout> getDailyWorkoutInRange(@Param("userId") Long userId, @Param("startDate") Long startDate, @Param("endDate") Long endDate);
}
