package com.fitlife.app.repository.jpa.exercise;


import com.fitlife.app.model.exercise.Target;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepository extends CrudRepository<Target,Long> {
}
