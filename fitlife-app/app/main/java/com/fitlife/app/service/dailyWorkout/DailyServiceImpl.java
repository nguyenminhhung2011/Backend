package com.fitlife.app.service.dailyWorkout;

import com.fitlife.app.repository.jpa.DailyWorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitlife.app.model.workout.DailyWorkout;
import com.fitlife.app.service.generic.GenericService;

@Service
public class DailyServiceImpl extends GenericService<DailyWorkout, Long, DailyWorkoutRepository> implements IDailyService{

	@Autowired
	public DailyServiceImpl(DailyWorkoutRepository genericRepository) {
		super(genericRepository);
	}

}
