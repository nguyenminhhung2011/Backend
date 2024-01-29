package com.fitlife.app.repository.jpa;

import com.fitlife.app.model.ActivitiesLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivitiesLogRepository extends JpaRepository<ActivitiesLog,Long > {
}
