package com.fitlife.app.Service.User;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.fitlife.app.DTO.DataClass.ResponseObject;
import com.fitlife.app.DTO.DataClass.User.UserDTO;
import com.fitlife.app.DTO.DataClass.User.UserProfileDTO;
import com.fitlife.app.DTO.DataClass.WorkoutPlanDTO;
import com.fitlife.app.DTO.Request.AddActivitiesLogRequest;
import com.fitlife.app.DTO.Request.ChangePasswordRequest;
import com.fitlife.app.DTO.Request.RegistrationRequest;
import com.fitlife.app.Exceptions.AppException.BadRequestException;
import com.fitlife.app.Exceptions.AppException.NotFoundException;
import com.fitlife.app.Model.NewsHealth.NewsHealth;
import com.fitlife.app.Repository.NewsHealthRepository;
import com.fitlife.app.Utils.Mapper.ActivitiesLogMapper;
import com.fitlife.app.Utils.Mapper.UserMapper;
import com.fitlife.app.Model.ActivitiesLog;
import com.fitlife.app.Model.Exercise.Exercise;
import com.fitlife.app.Model.User.UserProfile;
import com.fitlife.app.Model.Workout.WorkoutPlan;
import com.fitlife.app.Repository.ActivitiesLogRepository;
import com.fitlife.app.Repository.Exercise.ExerciseRepository;
import com.fitlife.app.Repository.User.UserProfileRepository;
import com.fitlife.app.Repository.WorkoutRepository;
import com.fitlife.app.Service.Generic.GenericService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fitlife.app.Model.User.User;
import com.fitlife.app.Repository.User.UserRepository;

@Service
public class UserServiceImpl extends GenericService<User,Long,UserRepository> implements IUserService {
	private final UserMapper userMapper;
	private final ModelMapper modelMapper;
	private final ActivitiesLogMapper actLogMapper;
	private final PasswordEncoder passwordEncoder;
	private final UserProfileRepository userProfileRepository;
	private final ActivitiesLogRepository actLogRepo;
	private final ExerciseRepository exerciseRepo;
	private final NewsHealthRepository newsHealthRepository;
	private final WorkoutRepository workoutRepository;

	public UserServiceImpl(UserRepository genericRepository, UserMapper userMapper, ModelMapper modelMapper, ActivitiesLogMapper actLogMapper, PasswordEncoder passwordEncoder, UserProfileRepository userProfileRepository, ActivitiesLogRepository actLogRepo, ExerciseRepository exerRepo, NewsHealthRepository newsHealthRepository, WorkoutRepository workoutRepository) {
		super(genericRepository);
		this.userMapper = userMapper;
		this.modelMapper = modelMapper;
		this.actLogMapper = actLogMapper;
		this.passwordEncoder = passwordEncoder;
		this.userProfileRepository = userProfileRepository;
		this.actLogRepo = actLogRepo;
		this.exerciseRepo = exerRepo;
		this.newsHealthRepository = newsHealthRepository;
		this.workoutRepository = workoutRepository;
	}

	public UserDTO getUserById(Long id) {
		final Optional<User> user = repository.findById(id);

		if (user.isEmpty()) {
			throw new NotFoundException("Can not found corresponding user");
		}

		return userMapper.userDTO(user.get());
	}



	@Override
	public UserProfileDTO getUserProfile(Long id){
		final Optional<UserProfile> userProfile = userProfileRepository.findById(id);
		if (userProfile.isEmpty()){
			throw new NotFoundException("Can not found corresponding user profile");
		}
		return userMapper.userProfileDTO(userProfile.get());
	}

	@Override
	public UserDTO getUserByUsername(String username) {
		Optional<User> result = repository.findByUsername(username);
		if (result.isEmpty()) {
			throw new NotFoundException("Can not found any user have name " + username);
		}
		return userMapper.userDTO(result.get());
	}

	@Override
	public ResponseObject changePassword(Long id, ChangePasswordRequest request) {
		User user = findById(id);
		if (passwordEncoder.matches(request.getOldPassword(), user.getPassword())) {
			user.setPassword(passwordEncoder.encode(request.getConfirmPassword()));
			repository.save(user);
			return new ResponseObject(HttpStatus.OK.value(), "Change user password successfully", null);
		}

		return new ResponseObject(HttpStatus.BAD_REQUEST.value(), "Change user password failed: Old password do not match", null);
	}

	@Override
	public UserDTO updateUserProfile(Long id, UserDTO userDTO) {
		User user = findById(id);
		UserProfile userProfile = user.getUserProfile();
		UserProfileDTO newUserProfileDTO = userDTO.getUserProfile();

		userProfile.setFrequency(newUserProfileDTO.getFrequency());
		userProfile.setWeight(newUserProfileDTO.getWeight());
		userProfile.setGender(newUserProfileDTO.getGender());
		userProfile.setHeight(newUserProfileDTO.getHeight());
		userProfile.setCurrentPlan(newUserProfileDTO.getCurrentPlan());
		userProfile.setPhone(newUserProfileDTO.getPhone());
		userProfile.setCreated(newUserProfileDTO.isCreated());

		userProfileRepository.save(userProfile);

		return userMapper.userDTO(user);
	}

	@Override
	public WorkoutPlanDTO changeCurrentPlan(Long userId, Long planId) {
		User user = findById(userId);
		UserProfile userProfile = user.getUserProfile();
		final Optional<WorkoutPlan> findPlan = workoutRepository.findById(planId);
		if(findPlan.isEmpty()){
			throw new NotFoundException("Can not found plan");
		}
		userProfile.setCurrentPlanId(planId);
		userProfileRepository.save(userProfile);
		return modelMapper.map(findPlan.get(), WorkoutPlanDTO.class);
	}

	@Override
	public WorkoutPlanDTO getCurrentPlan(Long userId) {
		User user = findById(userId);
		UserProfile userProfile = user.getUserProfile();
			final Optional<WorkoutPlan> workoutPlanOptional = workoutRepository.findById(userProfile.getCurrentPlanId());
		if(workoutPlanOptional.isEmpty()){
			throw new NotFoundException("Not found");
		}
		return modelMapper.map(workoutPlanOptional.get(),WorkoutPlanDTO.class);
	}

	@Override
	public ResponseObject addActivityLog(Long userId, AddActivitiesLogRequest dto) throws BadRequestException {
		User user = findById(userId);
		UserProfile userProfile = user.getUserProfile();

		Optional< WorkoutPlan> workoutPlanOptional = workoutRepository.findById(dto.workoutId());
		if (workoutPlanOptional.isEmpty()){
			throw new NotFoundException("Can not found the corresponding workout plan");
		}

		try{
			ActivitiesLog activitiesLog = ActivitiesLog.builder()
					.time(Instant.now().getEpochSecond())
					.userProfile(userProfile)
					.workoutPlan(workoutPlanOptional.get())
					.build();

			activitiesLog.setUserProfile(userProfile);

			return new ResponseObject(HttpStatus.OK.value(),
					"Add activity log successfully",
					actLogMapper.activitiesLogDTO(activitiesLog));
		}
		catch (Exception e){
			throw new BadRequestException(e.getMessage());
		}
	}

	@Override
	public ResponseObject addFavoriteExercise(Long userId,Long exeId)
			throws BadRequestException{

		User user = findById(userId);
		UserProfile userProfile = user.getUserProfile();

		var status = "";


		Optional<Exercise> exerciseOptional = exerciseRepo.findById(exeId);
		if (exerciseOptional.isEmpty()){
			throw new NotFoundException("Can not found the corresponding exercise");
		}

		try{
			final var exercise = exerciseOptional.get();
			if(exercise.getFavoriteUser().contains(userProfile)) {
				exercise.getFavoriteUser().removeIf(item -> Objects.equals(item.getId(), userId));
				status  = "Remove favorite exercise successfully";
			}
			else {
				exercise.getFavoriteUser().add(userProfile);
				status = "Add favorite exercise successfully";
			}
			userProfileRepository.save(userProfile);
			exerciseRepo.save(exercise);
			return new ResponseObject(HttpStatus.OK.value(),status,null);
		}
		catch (Exception e){
			throw new BadRequestException(e.getMessage());
		}
	}

	@Override
	public ResponseObject addFavoriteNews(Long userId, Long newsId) throws BadRequestException {
		User user = findById(userId);
		UserProfile userProfile = user.getUserProfile();

		var status = "";

		Optional<NewsHealth> newsHealthOptional = newsHealthRepository.findById(newsId);

		if(newsHealthOptional.isEmpty()){
			throw new NotFoundException("Can not found news");
		}
		try {
			final var news = newsHealthOptional.get();
			if(news.getNewsUser().contains(userProfile)) {
				news.getNewsUser().removeIf(item -> Objects.equals(item.getId(), userId));
				status  = "Remove favorite news successfully";
			}else {
				news.getNewsUser().add(userProfile);
				status  = "Add favorite news successfully";
			}
			userProfileRepository.save(userProfile);
			newsHealthRepository.save(news);
			return new ResponseObject(HttpStatus.OK.value(), status, null);

		}catch (Exception e){
			throw new BadRequestException(e.getMessage());
		}
	}

	@Override
	public List<UserDTO> getAllUser() {
		return repository.findAll().stream().map(userMapper::userDTO).toList();
	}

	@Override
	public UserDTO addNewUser(RegistrationRequest request) {
		User newUser = new User(
				null,
				request.getUsername(),
				passwordEncoder.encode(request.getPassword()) ,
				null,
				null
		);
		final User savedUser = repository.save(newUser);

		UserProfile userProfile = UserProfile
				.builder()
				.id(null)
				.user(savedUser)
				.build();
		final UserProfile saveUserProfile = userProfileRepository.save(userProfile);
		savedUser.setUserProfile(saveUserProfile);
		return userMapper.userDTO(savedUser);
	}

}

