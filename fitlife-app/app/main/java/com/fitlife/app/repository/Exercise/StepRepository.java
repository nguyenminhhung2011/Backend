package com.fitlife.app.repository.Exercise;

import com.fitlife.app.model.Exercise.Steps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends JpaRepository<Steps,Long> {
}
