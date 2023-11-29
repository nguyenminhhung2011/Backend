package com.FitnessApp.Repository.Exercise;

import com.FitnessApp.Model.Exercise.Equipment;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EquipmentRepository extends CrudRepository<Equipment,Long> {
}
