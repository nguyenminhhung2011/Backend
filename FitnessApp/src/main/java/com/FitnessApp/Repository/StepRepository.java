package com.FitnessApp.Repository;

import com.FitnessApp.Model.Steps;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StepRepository extends JpaRepository<Steps,Long> {
}
