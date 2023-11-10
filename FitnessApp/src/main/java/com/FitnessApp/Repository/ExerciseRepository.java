package com.FitnessApp.Repository;

import com.FitnessApp.Model.Exercise.Exercise;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends GenericSearchRepository<Exercise,Long>{
}
