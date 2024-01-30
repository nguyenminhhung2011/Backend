package com.fitlife.app.service.session;

import com.fitlife.app.dataClass.dto.SessionDTO;
import com.fitlife.app.dataClass.request.CustomExerciseRequest;
import com.fitlife.app.dataClass.request.session.SessionRequest;
import com.fitlife.app.dataClass.request.session.UpdateSettingSessionRequest;
import com.fitlife.app.dataClass.response.CustomExerciseResponse;
import com.fitlife.app.exceptions.appException.BadRequestException;
import com.fitlife.app.exceptions.appException.NotFoundException;
import com.fitlife.app.model.workout.DailyWorkout;
import com.fitlife.app.model.exercise.CustomExercise;
import com.fitlife.app.model.exercise.Exercise;
import com.fitlife.app.model.workout.WorkoutPlan;
import com.fitlife.app.repository.jpa.DailyWorkoutRepository;
import com.fitlife.app.repository.jpa.SessionRepository;
import com.fitlife.app.repository.jpa.WorkoutRepository;
import com.fitlife.app.repository.jpa.exercise.ExerciseRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fitlife.app.model.session.Session;
import com.fitlife.app.service.generic.GenericService;

import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class SessionServiceImpl extends GenericService<Session, Long, SessionRepository> implements  ISessionService{


	private final ModelMapper modelMapper;
	private final DailyWorkoutRepository dailyWorkoutRepository;
	private final WorkoutRepository workoutRepository;
	private final ExerciseRepository exerciseRepository;




	@Autowired
	public SessionServiceImpl(SessionRepository genericService, ModelMapper modelMapper, DailyWorkoutRepository dailyWorkoutRepository, ExerciseRepository exerciseRepository, WorkoutRepository workoutRepository) {
		super(genericService);
		this.modelMapper = modelMapper;
		this.exerciseRepository = exerciseRepository;
		this.dailyWorkoutRepository = dailyWorkoutRepository;
		this.workoutRepository = workoutRepository;
	}

	@Override
	public SessionDTO createSession(SessionRequest req) throws BadRequestException {
		try {
			final Optional<DailyWorkout> daily = dailyWorkoutRepository.findById(req.getDailyWorkouts());

			if(daily.isEmpty()) {
				throw new NotFoundException("Can not find daily workout");
			}
			DailyWorkout dailyData = daily.get();


			Session session = Session.builder()
					.description(req.getDescription())
					.name(req.getName())
					.timePerLesson(req.getTimePerLesson())
					.randomMix(req.getRandomMix())
					.calcTarget(req.getCalcTarget())
					.level(req.getLevel())
					.transferTime(req.getTransferTime())
					.numberRound(req.getNumberRound())
					.breakTime(req.getBreakTime())
					.done(false)
					.calcCompleted(0)
					.startWithBoot(req.getStartWithBoot()).build();
			List<Session> currentSession = dailyData.getSessions();

			session.setDailyWorkouts(dailyData);
			currentSession.add(session);
			dailyData.setSessions(currentSession);
			dailyWorkoutRepository.save(dailyData);

			return modelMapper.map(session, SessionDTO.class);
		}catch (Exception e){
			throw new BadRequestException(e.getMessage());
		}
	}

	@Override
	public SessionDTO updateSettingSession(UpdateSettingSessionRequest request, Long id) throws BadRequestException {
		try {

			final Session session = findById(id);

			session.setName(request.getName());
			session.setLevel(request.getLevel());
			session.setRandomMix(request.getRandomMix());
			session.setBreakTime(request.getBreakTime());
			session.setCalcTarget(request.getCalcTarget());
			session.setNumberRound(request.getNumberRound());
			session.setDescription(request.getDescription());
			session.setTransferTime(request.getTransferTime());
			session.setTimePerLesson(request.getTimePerLesson());
			session.setStartWithBoot(request.getStartWithBoot());

			repository.save(session);

			return modelMapper.map(session,SessionDTO.class);
		} catch (Exception e){
			throw new BadRequestException(e.getMessage());
		}
	}

	@Override
	public List<SessionDTO> getAllSession(String dailyID) throws BadRequestException {
		try {
			final Optional<DailyWorkout> daily = dailyWorkoutRepository.findById(Long.parseLong(dailyID));

			if(daily.isEmpty()) {
				throw new NotFoundException("Can not find daily workout");
			}
			return daily.get().getSessions().stream().map(item ->  modelMapper.map(item, SessionDTO.class)).toList();
		} catch (Exception e){
			throw new BadRequestException(e.getMessage());
		}
	}

	@Override
	public CustomExerciseResponse createCustomExercise(CustomExerciseRequest req, String sessionId) throws BadRequestException {
		try {
			final Session session = findById(Long.parseLong(sessionId));

			CustomExercise customExercise = CustomExercise.builder()
					.rep(req.getRep())
					.weight(req.getWeight())
					.time(req.getTime())
					.difficulty(req.getDifficulty())
					.session(session)
					.calories((new Random()).nextInt(100, 400)) ///🐛[dummy code]
					.build();

			final Optional<Exercise> exercise = exerciseRepository.findById(req.getExercise());
			if(exercise.isEmpty()){
				throw new NotFoundException("Can not found exercise with id " + req.getExercise());
			}
			final  Exercise exerciseData = exercise.get();

			customExercise.setExercise(exerciseData);
			List<CustomExercise> sessionExercise =	 session.getCustomExercise();
			sessionExercise.add(customExercise);

			session.setCustomExercise(sessionExercise);

			save(session);

			return modelMapper.map(customExercise,CustomExerciseResponse.class);
		}catch (Exception e){
			throw new BadRequestException(e.getMessage());
		}
	}

	@Override
	public SessionDTO getSessionById(String id) {
		return modelMapper.map( findById(Long.parseLong(id)), SessionDTO.class);
	}


	@Override
	public SessionDTO completeSession(String id) throws BadRequestException {
		try{
			final Session session = findById(Long.parseLong(id));
			var totalCalories = 0;
			for (CustomExercise item: session.getCustomExercise()) {
				totalCalories += item.getCalories();
			}
			session.setCalcCompleted(totalCalories * session.getNumberRound());
			session.setDone(true);

			repository.save(session);

			return modelMapper.map(session, SessionDTO.class);

		} catch (Exception e) {
			throw new BadRequestException(e.getMessage());
		}
	}


	@Override
	public List<SessionDTO> getUpComingSession() throws BadRequestException {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			final long currentTime = new Date().getTime();
			final String formatCurrentDate = sdf.format(currentTime);
			final List<WorkoutPlan> getActivePlan = workoutRepository.getActiveWorkoutPlan(currentTime);
			final List<Session> result = new ArrayList<>();

			for (WorkoutPlan item: getActivePlan) {
				for (DailyWorkout daily: item.getDailyWorkouts()) {
					if(sdf.format(daily.getTime()).equals(formatCurrentDate)){
						result.addAll(daily.getSessions());
						break;
					}
				}
			}

			return result.stream().map(item -> modelMapper.map(item, SessionDTO.class)).toList();
		}catch (Exception e){
			throw new BadRequestException(e.getMessage());
		}
	}
}
