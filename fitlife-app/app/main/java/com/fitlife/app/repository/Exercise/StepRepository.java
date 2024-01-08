package com.fitlife.app.Repository.Exercise;

import com.fitlife.app.Model.Exercise.Steps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends JpaRepository<Steps,Long> {
}
