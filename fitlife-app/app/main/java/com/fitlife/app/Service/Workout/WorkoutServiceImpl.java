package com.fitlife.app.Service.Workout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import com.fitlife.app.DTO.Request.DailyWorkoutReq;
import com.fitlife.app.DTO.Request.WorkoutPlanReq;
import com.fitlife.app.DTO.Response.WorkoutPlanResponse;
import com.fitlife.app.Exceptions.AppException.BadRequestException;
import com.fitlife.app.Exceptions.AppException.NotFoundException;
import com.fitlife.app.Model.AISupport;
import com.fitlife.app.Model.DailyWorkout;
import com.fitlife.app.Model.User.UserProfile;
import com.fitlife.app.Repository.User.UserProfileRepository;
import com.fitlife.app.Utils.Enums.PlanType;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fitlife.app.DTO.DataClass.WorkoutPlanDTO;
import com.fitlife.app.Model.WorkoutPlan;
import com.fitlife.app.Repository.WorkoutRepository;
import com.fitlife.app.Service.Generic.GenericService;


@Service
public class WorkoutServiceImpl extends GenericService<WorkoutPlan, Long, WorkoutRepository> implements IWorkoutService {

	public WorkoutServiceImpl(WorkoutRepository genericRepository, ModelMapper modelMapper, UserProfileRepository userRepository) {
		super(genericRepository);
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
	}

	private final ModelMapper modelMapper;
	private  final UserProfileRepository userRepository;

	@Override
	public List<WorkoutPlan> getWorkoutPlansByUserProfileId(Long userProfileId) {
		return repository.findByUserProfileId(userProfileId);
	}

	@Override
	public List<DailyWorkout> getAllDailyPlan(String id) throws BadRequestException {
		try{
			WorkoutPlan workoutPlan = findById(Long.parseLong(id));

			List<DailyWorkout> currentDaily = workoutPlan.getDailyWorkouts();

			for (DailyWorkout dailyWorkout : currentDaily) {
				dailyWorkout.setWorkoutPlan(null);
			}

			return currentDaily;

		}catch (Exception e ){
			throw new BadRequestException(e.getMessage());
		}
	}

	@Override
	public Page<Object> searchWorkoutPlans(Long id, String name, Date startDate, Date endDate, Pageable pageable) {
		Page<WorkoutPlan> workoutPlans;

		if (startDate == null && endDate == null) {
			workoutPlans = repository.findByUserProfile_IdAndNameContainingIgnoreCase(id, name, pageable);
		} else {
			workoutPlans = repository.findByUserProfile_IdAndNameContainingIgnoreCaseAndStartDateBetween(id, name,
					startDate, endDate, pageable);
		}

		return workoutPlans.map(workoutPlan -> modelMapper.map(workoutPlan, WorkoutPlanDTO.class));
	}


	@Override
	public WorkoutPlanResponse createDailyPlan(DailyWorkoutReq req, Long id) throws BadRequestException {
		try {
			final Optional<WorkoutPlan> workoutPlan = repository.findById(id);
			if (workoutPlan.isEmpty()) {
				throw new NotFoundException("Can not found plan");
			}
			final WorkoutPlan workoutPlanData = workoutPlan.get();

			DailyWorkout newDaily = DailyWorkout.builder()
					.name(req.getName())
					.description(req.getDescription())
					.breakTime(req.getBreakTime())
					.caloTarget(req.getCaloTarget())
					.time(req.getTime())
					.execPerRound(req.getExecPerRound())
					.numberRound(req.getNumberRound())
					.timeForEachExe(req.getTimeForEachExe())
					.workoutDuration(req.getWorkoutDuration()).build();

			final List<DailyWorkout> currentDaily = workoutPlanData.getDailyWorkouts();
			currentDaily.add(newDaily);

			workoutPlanData.setDailyWorkouts(currentDaily);
			save(workoutPlanData);
			return WorkoutPlanResponse.getFrom(workoutPlanData);

		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@Override
	public WorkoutPlanResponse createWorkoutPlan(WorkoutPlanReq workoutPlanDTO, Long idUser ) throws BadRequestException {
		try{
			WorkoutPlan workoutPlan = new WorkoutPlan();

			workoutPlan.setName(workoutPlanDTO.getName());
			workoutPlan.setDescription(workoutPlanDTO.getDescription());
			workoutPlan.setStartDate(workoutPlanDTO.getStartDate());
			workoutPlan.setEndDate(workoutPlanDTO.getEndDate());
			workoutPlan.setType(PlanType.valueOf(workoutPlanDTO.getType()));

			Optional<UserProfile> user = userRepository.findById(idUser);

			if(user.isEmpty()){
				throw new NotFoundException("User not found");
			}
			workoutPlan.setUserProfile(user.get());

			if (workoutPlanDTO.getType().equals("AI")) {
				AISupport ai = new AISupport();
				ai.setFitnessGoal(workoutPlanDTO.getFitnessGoal());
				ai.setFitnessLevelCurrent(workoutPlanDTO.getFitnessLevelCurrent());
				ai.setPreference(workoutPlanDTO.getPreference());
				ai.setWorkoutplan(workoutPlan);
				workoutPlan.setAiSupport(ai);
			}
			save(workoutPlan);
			return WorkoutPlanResponse.getFrom(workoutPlan);


		}catch (Exception e){

			throw new BadRequestException(e.getMessage());
		}
	}

	@Override
	public List<WorkoutPlanResponse> getAll() {
		List<WorkoutPlanResponse> response = new ArrayList<>();
		for (WorkoutPlan item: findAll()) 	{
			response.add(WorkoutPlanResponse.getFrom(item));
		}
		return response;
	}

	@Override
	public List<WorkoutPlanResponse> getMyWorkoutPlan(Long idUser) {
		List<WorkoutPlanResponse> response = new ArrayList<>();
		for (WorkoutPlan item: getWorkoutPlansByUserProfileId(idUser)) 	{
			response.add(WorkoutPlanResponse.getFrom(item));
		}
		return response;
	}

}
