package com.fitlife.app.repository.Exercise;

import com.fitlife.app.model.Exercise.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment,Long> {
}
