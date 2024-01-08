package com.fitlife.app.Repository.Exercise;


import com.fitlife.app.Model.Exercise.Target;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepository extends CrudRepository<Target,Long> {
}
