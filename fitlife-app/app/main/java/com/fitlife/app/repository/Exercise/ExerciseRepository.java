package com.fitlife.app.repository.Exercise;

import com.fitlife.app.model.Exercise.Exercise;
import com.fitlife.app.repository.Generic.GenericSearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends GenericSearchRepository<Exercise,Long> {
}
