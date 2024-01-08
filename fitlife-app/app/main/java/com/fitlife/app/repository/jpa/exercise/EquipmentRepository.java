package com.fitlife.app.repository.jpa.exercise;

import com.fitlife.app.model.exercise.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment,Long> {
}
