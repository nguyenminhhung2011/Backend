package com.fitlife.app.repository.Exercise;

import com.fitlife.app.model.Exercise.BodyPart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyPartRepository extends CrudRepository<BodyPart,Long> {
}
