package com.fitlife.app.Repository;

import com.fitlife.app.Model.ActivitiesLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ActivitiesLogRepository extends JpaRepository<ActivitiesLog,Long > {
}
