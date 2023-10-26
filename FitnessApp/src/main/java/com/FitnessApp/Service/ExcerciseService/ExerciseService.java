package com.FitnessApp.Service.ExcerciseService;

import org.springframework.stereotype.Service;

import com.FitnessApp.Model.Exercise;
import com.FitnessApp.Repository.ExerciseRepository;
import com.FitnessApp.Service.Generic.GenericService;

@Service
public class ExerciseService extends GenericService<Exercise> implements IExerciseSV {
	public ExerciseService(ExerciseRepository gmRepository) {
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
