package com.fitlife.app.service.DailyWorkout;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitlife.app.model.Workout.DailyWorkout;
import com.fitlife.app.repository.DailyWorkoutRepository;
import com.fitlife.app.service.Generic.GenericService;

@Service
public class DailyServiceImpl extends GenericService<DailyWorkout, Long, DailyWorkoutRepository> implements IDailyService{

	@Autowired
	public DailyServiceImpl(DailyWorkoutRepository genericRepository) {
		super(genericRepository);
	}

}
