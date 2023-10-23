package com.FitnessApp.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.FitnessApp.Model.Gymer;

@Repository
public interface GymerRepository extends JpaRepository<Gymer, Long> {

}
