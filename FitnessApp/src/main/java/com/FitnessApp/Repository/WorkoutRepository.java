package com.FitnessApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FitnessApp.Model.WorkoutPlan;

public interface WorkoutRepository extends JpaRepository<WorkoutPlan, Long> {

}
