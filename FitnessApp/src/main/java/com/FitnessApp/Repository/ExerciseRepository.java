package com.FitnessApp.Repository;

import com.FitnessApp.Service.ExcerciseService.ExerciseService;
import com.FitnessApp.Service.ExcerciseService.ExerciseSpecification;
import org.springframework.data.jpa.repository.JpaRepository;

import com.FitnessApp.Model.Exercise;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ExerciseRepository extends GenericSearchRepository<Exercise,Long>{

}
