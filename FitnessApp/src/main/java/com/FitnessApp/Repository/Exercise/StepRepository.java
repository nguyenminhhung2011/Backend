package com.FitnessApp.Repository.Exercise;

import com.FitnessApp.Model.Steps;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Steps,Long> {
}
