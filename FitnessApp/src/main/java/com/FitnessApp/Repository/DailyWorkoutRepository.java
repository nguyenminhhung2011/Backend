package com.FitnessApp.Repository;

import com.FitnessApp.Model.DailyWorkout;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DailyWorkoutRepository extends JpaRepository<DailyWorkout, Long> {
}
