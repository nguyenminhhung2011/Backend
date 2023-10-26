package com.FitnessApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.FitnessApp.Model.Exercise;

public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

}
