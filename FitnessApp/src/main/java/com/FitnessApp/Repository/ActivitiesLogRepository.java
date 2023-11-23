package com.FitnessApp.Repository;

import com.FitnessApp.Model.ActivitiesLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivitiesLogRepository extends JpaRepository<ActivitiesLog,Long > {
}
