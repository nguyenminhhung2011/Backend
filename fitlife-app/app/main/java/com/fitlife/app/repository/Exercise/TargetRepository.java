package com.fitlife.app.repository.Exercise;


import com.fitlife.app.model.Exercise.Target;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepository extends CrudRepository<Target,Long> {
}
