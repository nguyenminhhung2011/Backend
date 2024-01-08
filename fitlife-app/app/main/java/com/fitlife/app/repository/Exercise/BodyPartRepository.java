package com.fitlife.app.Repository.Exercise;

import com.fitlife.app.Model.Exercise.BodyPart;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BodyPartRepository extends CrudRepository<BodyPart,Long> {
}
