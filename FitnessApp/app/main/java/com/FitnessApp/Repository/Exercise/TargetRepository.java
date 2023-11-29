package com.FitnessApp.Repository.Exercise;


import com.FitnessApp.Model.Exercise.Target;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TargetRepository extends CrudRepository<Target,Long> {
}
