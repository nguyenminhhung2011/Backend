package com.fitlife.app.repository.jpa.exercise;

import com.fitlife.app.model.exercise.BodyPart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyPartRepository extends CrudRepository<BodyPart,Long> {
}
