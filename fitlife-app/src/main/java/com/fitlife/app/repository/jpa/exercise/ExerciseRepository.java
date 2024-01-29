package com.fitlife.app.repository.jpa.exercise;

import com.fitlife.app.model.exercise.Exercise;
import com.fitlife.app.repository.jpa.generic.GenericSearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends GenericSearchRepository<Exercise,Long> {
}
