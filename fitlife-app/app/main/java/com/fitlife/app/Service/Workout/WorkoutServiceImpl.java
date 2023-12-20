package com.fitlife.app.Service.Workout;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.fitlife.app.DTO.DataClass.DailyWorkoutDTO;
import com.fitlife.app.DTO.Request.DailyWorkoutRequest;
import com.fitlife.app.DTO.Request.GetChartRequest;
import com.fitlife.app.DTO.Request.WorkoutPlanRequest;
import com.fitlife.app.DTO.Response.ChartResponse;
import com.fitlife.app.DTO.Response.FitOverviewResponse;
import com.fitlife.app.DTO.Response.WorkoutPlanResponse;
import com.fitlife.app.Exceptions.AppException.BadRequestException;
import com.fitlife.app.Exceptions.AppException.NotFoundException;
import com.fitlife.app.Model.AISupport;
import com.fitlife.app.Model.Workout.DailyWorkout;
import com.fitlife.app.Model.User.UserProfile;
import com.fitlife.app.Model.session.Session;
import com.fitlife.app.Repository.DailyWorkoutRepository;
import com.fitlife.app.Repository.User.UserProfileRepository;
import com.fitlife.app.Utils.Enums.PlanType;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.fitlife.app.DTO.DataClass.WorkoutPlanDTO;
import com.fitlife.app.Model.Workout.WorkoutPlan;
import com.fitlife.app.Repository.WorkoutRepository;
import com.fitlife.app.Service.Generic.GenericService;


@Service
public class WorkoutServiceImpl extends GenericService<WorkoutPlan, Long, WorkoutRepository> implements IWorkoutService {

	public WorkoutServiceImpl(WorkoutRepository genericRepository, ModelMapper modelMapper, UserProfileRepository userRepository,  DailyWorkoutRepository dailyWorkoutRepository) {
		super(genericRepository);
		this.modelMapper = modelMapper;
		this.userRepository = userRepository;
		this.dailyWorkoutRepository = dailyWorkoutRepository;
	}

	private final ModelMapper modelMapper;
	private  final UserProfileRepository userRepository;
	private  final DailyWorkoutRepository dailyWorkoutRepository;

	@Override
	public List<WorkoutPlan> getWorkoutPlansByUserProfileId(Long userProfileId) {
		return repository.findByUserProfileId(userProfileId);
	}

	@Override
	public List<DailyWorkoutDTO> getAllDailyPlan(String id) throws BadRequestException {
		try{
			WorkoutPlan workoutPlan = findById(Long.parseLong(id));

			List<DailyWorkout> currentDaily = workoutPlan.getDailyWorkouts();

			for (DailyWorkout dailyWorkout : currentDaily) {
				dailyWorkout.setWorkoutPlan(null);
			}

			return currentDaily.stream().map(item -> modelMapper.map(item, DailyWorkoutDTO.class)).toList();

		}catch (Exception e ){
			throw new BadRequestException(e.getMessage());
		}
	}

	private int getChartViewInArray(Long time, List<ChartResponse> arr) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		final String formatTime = sdf.format(time);

		OptionalInt index = IntStream.range(0, arr.size())
				.filter(i -> sdf.format(arr.get(i).getTime()).equals(formatTime))
				.findFirst();

		return index.orElse(0);
	}

	@Override
	public FitOverviewResponse getChartView(GetChartRequest request, Long userId) {
		LocalDate startDate = Instant.ofEpochMilli(request.startDate()).atZone(ZoneId.systemDefault()).toLocalDate();
		LocalDate endDate = Instant.ofEpochMilli(request.endDate()).atZone(ZoneId.systemDefault()).toLocalDate();

		List<ChartResponse> chartResponse = startDate.datesUntil(endDate).map(item ->
				new ChartResponse(0, item.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli())
		).collect(Collectors.toList());

		List<DailyWorkout> dailyWorkouts = dailyWorkoutRepository.getDailyWorkoutInRange(userId, request.startDate(), request.endDate());

		int totalSession = 0;
		int totalSessionCompleted = 0;
		int totalCalories = 0;

		for (DailyWorkout dailyWorkout : dailyWorkouts) {
			int calories = chartResponse.get(getChartViewInArray(dailyWorkout.getTime(), chartResponse)).getCalories();
			for (Session session : dailyWorkout.getSessions()) {
				totalSession += 1;
				if(session.getDone()){
					totalSessionCompleted += 1;
				}
				calories += session.getCalcCompleted();
			}
			totalCalories += calories;
			chartResponse.get(getChartViewInArray(dailyWorkout.getTime(), chartResponse)).setCalories(calories);
		}
		final FitOverviewResponse result = new FitOverviewResponse();
		result.setChartData(chartResponse);
		if (totalSession == 0) {
			result.setTodoPercent(0);
		} else {
			result.setTodoPercent((double) totalSessionCompleted / totalSession);
		}
		result.setCalories(totalCalories);
		return result;

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
	public DailyWorkoutDTO createDailyPlan(DailyWorkoutRequest req, Long id) throws BadRequestException {
		try {


			final Optional<WorkoutPlan> workoutPlan = repository.findById(id);
			if (workoutPlan.isEmpty()) {
				throw new NotFoundException("Can not found plan");
			}


			final WorkoutPlan workoutPlanData = workoutPlan.get();


			if(req.getTime() < workoutPlanData.getStartDate() || req.getTime() > workoutPlanData.getEndDate()){
				throw new BadRequestException("Time is out of range [startDate - endDate]");
			}
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");


			final Optional<DailyWorkout> isExistedDate = workoutPlanData.getDailyWorkouts().stream().filter(
					item -> sdf.format(item.getTime()).equals(sdf.format(req.getTime()))
			).findFirst();

			if(isExistedDate.isPresent()){
				throw new BadRequestException("Date was existed in plan");
			}

			DailyWorkout newDaily = DailyWorkout.builder()
					.name(req.getName())
					.description(req.getDescription())
					.time(req.getTime())
					.workoutPlan(workoutPlanData)
					.build();


			final List<DailyWorkout> currentDaily = workoutPlanData.getDailyWorkouts();
			currentDaily.add(newDaily);

			workoutPlanData.setDailyWorkouts(currentDaily);
			save(workoutPlanData);
			return modelMapper.map(newDaily, DailyWorkoutDTO.class);

		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}

	@Override
	public WorkoutPlanResponse createWorkoutPlan(WorkoutPlanRequest workoutPlanDTO, Long idUser ) throws BadRequestException {
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
		return getWorkoutPlansByUserProfileId(idUser).stream().map(WorkoutPlanResponse::getFrom).toList();
	}

	@Override
	public List<WorkoutPlanResponse> getActiveWorkoutPlan(Long time) throws BadRequestException {
		try {
			return repository.getActiveWorkoutPlan(time).stream()
					.map(item -> modelMapper.map(item, WorkoutPlanResponse.class))
					.toList();
		}catch (Exception e){
			throw new BadRequestException(e.getMessage());
		}
	}

}
