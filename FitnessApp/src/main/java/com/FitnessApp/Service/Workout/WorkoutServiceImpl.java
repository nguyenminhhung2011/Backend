package com.FitnessApp.Service.Workout;

import java.util.Date;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.FitnessApp.DTO.DataClass.WorkoutPlanDTO;
import com.FitnessApp.Model.WorkoutPlan;
import com.FitnessApp.Repository.WorkoutRepository;
import com.FitnessApp.Service.Generic.GenericService;

@Service
public class WorkoutServiceImpl extends GenericService<WorkoutPlan, Long, WorkoutRepository> {
	//	public WorkoutServiceImpl(WorkoutRepository gmRepository) {
//		super(gmRepository);
//
//	}
	@Autowired
	public WorkoutServiceImpl(WorkoutRepository workoutPlanRepository, ModelMapper modelMapper) {
		super(workoutPlanRepository);
		this.modelMapper = modelMapper;
	}

	@Autowired
	WorkoutRepository workoutRP;
	@Autowired
	private final ModelMapper modelMapper;

	public List<WorkoutPlan> getWorkoutPlansByUserProfileId(Long userProfileId) {
		return workoutRP.findByUserProfileId(userProfileId);
	}

	public Page<Object> searchWorkoutPlans(Long id, String name, Date startDate, Date endDate, Pageable pageable) {
		Page<WorkoutPlan> workoutPlans;

		if (startDate == null && endDate == null) {
			workoutPlans = workoutRP.findByUserProfile_IdAndNameContainingIgnoreCase(id, name, pageable);
		} else {
			workoutPlans = workoutRP.findByUserProfile_IdAndNameContainingIgnoreCaseAndStartDateBetween(id, name,
					startDate, endDate, pageable);
		}

		return workoutPlans.map(workoutPlan -> modelMapper.map(workoutPlan, WorkoutPlanDTO.class));
	}
}
//	public WorkoutPlan createWorkoutPlan(WorkoutPlanReq workoutPlanDTO, Long id) {
//
//		WorkoutPlan workoutPlan = new WorkoutPlan();
//		workoutPlan.setName(workoutPlanDTO.getName());
//		workoutPlan.setDescription(workoutPlanDTO.getDescription());
//		workoutPlan.setStartDate(workoutPlanDTO.getStartDate());
//		workoutPlan.setEndDate(workoutPlanDTO.getEndDate());
//		workoutPlan.setType(PlanType.valueOf(workoutPlanDTO.getType()));
//		workoutPlan.setUserProfile(id);
//		if (workoutPlanDTO.getType().equals("AI")) {
//			AISupport ai = new AISupport();
//			ai.setFitnessGoal(workoutPlanDTO.getFitnessGoal());
//			ai.setFitnessLevelCurrent(workoutPlanDTO.getFitnessLevelCurrent());
//			ai.setPreference(workoutPlanDTO.getPreference());
//			ai.setWorkoutplan(workoutPlan);
//			workoutPlan.setAiSupport(ai);
//		}
//		// You may need additional logic or validations here before saving the entity
//
//		return repository.save(workoutPlan);
//
//	}

//	public List<Gymer> getCVByUsername(String name) {
//		return ((GymerRepository) genericRepository).get(name);
//	}
//
//	public Optional<AnalysisData> analysData(String name) {
//		AnalysisData data = ((CvRepository) genericRepository).getAnalysData(name);
//		Optional<AnalysisData> result = null;
//		return result.of(data);
//	}