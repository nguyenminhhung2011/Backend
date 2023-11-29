package com.FitnessApp.Service.DailyWorkout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.FitnessApp.Model.DailyWorkout;
import com.FitnessApp.Repository.DailyWorkoutRepository;
import com.FitnessApp.Service.Generic.GenericService;

@Service
public class DailyServiceImpl extends GenericService<DailyWorkout, Long, DailyWorkoutRepository> {

	@Autowired
	DailyWorkoutRepository dailyRP;

	@Autowired
	public DailyServiceImpl(DailyWorkoutRepository workoutPlanRepository) {
		super(workoutPlanRepository);

	}

}
