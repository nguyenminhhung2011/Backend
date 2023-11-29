package com.FitnessApp.Repository.Exercise;

import com.FitnessApp.Model.Exercise.Exercise;
import com.FitnessApp.Repository.Generic.GenericSearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends GenericSearchRepository<Exercise,Long> {
}
