package com.fitlife.app.repository.jpa.exercise;

import com.fitlife.app.model.exercise.Steps;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StepRepository extends JpaRepository<Steps,Long> {
}
