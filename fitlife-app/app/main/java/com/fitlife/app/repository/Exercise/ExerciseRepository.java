package com.fitlife.app.Repository.Exercise;

import com.fitlife.app.Model.Exercise.Exercise;
import com.fitlife.app.Repository.Generic.GenericSearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExerciseRepository extends GenericSearchRepository<Exercise,Long> {
}
