package com.fitlife.app.Repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.fitlife.app.Model.Workout.WorkoutPlan;
import org.springframework.data.jpa.repository.Query;

public interface WorkoutRepository extends JpaRepository<WorkoutPlan, Long> {

	List<WorkoutPlan> findByUserProfileId(Long userProfileId);
	Page<WorkoutPlan> findByUserProfile_IdAndStartDateBetween(Long userId, Date startDate, Date endDate,
			Pageable pageable);

	Page<WorkoutPlan> findByUserProfile_IdAndNameContainingIgnoreCase(Long userId, String name, Pageable pageable);

	Page<WorkoutPlan> findByUserProfile_IdAndNameContainingIgnoreCaseAndStartDateBetween(Long userId, String name,
			Date startDate, Date endDate, Pageable pageable);

	@Query("select r from WorkoutPlan r where :time between r.startDate and r.endDate")
	List<WorkoutPlan> getActiveWorkoutPlan(Long time);
}
