package com.FitnessApp.Service.WorkoutService;

import org.springframework.stereotype.Service;

import com.FitnessApp.Model.WorkoutPlan;
import com.FitnessApp.Repository.WorkoutRepository;
import com.FitnessApp.Service.Generic.GenericService;

@Service
public class WorkoutService extends GenericService<WorkoutPlan> implements IWorkoutSV {
	public WorkoutService(WorkoutRepository gmRepository) {
		super(gmRepository);
	}

//	public List<Gymer> getCVByUsername(String name) {
//		return ((GymerRepository) genericRepository).get(name);
//	}
//
//	public Optional<AnalysisData> analysData(String name) {
//		AnalysisData data = ((CvRepository) genericRepository).getAnalysData(name);
//		Optional<AnalysisData> result = null;
//		return result.of(data);
//	}

}
