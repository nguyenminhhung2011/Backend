package com.FitnessApp.Service.User;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import com.FitnessApp.DTO.DataClass.ResponseObject;
import com.FitnessApp.DTO.DataClass.User.UserDTO;
import com.FitnessApp.DTO.DataClass.User.UserProfileDTO;
import com.FitnessApp.DTO.Request.AddActivitiesLogRequest;
import com.FitnessApp.DTO.Request.ChangePasswordRequest;
import com.FitnessApp.DTO.Request.RegistrationRequest;
import com.FitnessApp.Exceptions.AppException.BadRequestException;
import com.FitnessApp.Exceptions.AppException.NotFoundException;
import com.FitnessApp.Utils.Mapper.ActivitiesLogMapper;
import com.FitnessApp.Utils.Mapper.UserMapper;
import com.FitnessApp.Model.ActivitiesLog;
import com.FitnessApp.Model.Exercise.Exercise;
import com.FitnessApp.Model.User.UserProfile;
import com.FitnessApp.Model.WorkoutPlan;
import com.FitnessApp.Repository.ActivitiesLogRepository;
import com.FitnessApp.Repository.Exercise.ExerciseRepository;
import com.FitnessApp.Repository.User.UserProfileRepository;
import com.FitnessApp.Repository.WorkoutRepository;
import com.FitnessApp.Service.Generic.GenericService;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.FitnessApp.Model.User.User;
import com.FitnessApp.Repository.User.UserRepository;

@Service
public class UserServiceImpl extends GenericService<User,Long,UserRepository> implements IUserService {
	private final UserMapper userMapper;
	private final ActivitiesLogMapper actLogMapper;
	private final PasswordEncoder passwordEncoder;
	private final UserProfileRepository userProfileRepository;
	private final ActivitiesLogRepository actLogRepo;
	private final ExerciseRepository exerciseRepo;
	private final WorkoutRepository workoutRepository;

	public UserServiceImpl(UserRepository genericRepository, UserMapper userMapper, ActivitiesLogMapper actLogMapper, PasswordEncoder passwordEncoder, UserProfileRepository userProfileRepository, ActivitiesLogRepository actLogRepo, ExerciseRepository exerRepo, WorkoutRepository workoutRepository) {
		super(genericRepository);
		this.userMapper = userMapper;
		this.actLogMapper = actLogMapper;
		this.passwordEncoder = passwordEncoder;
		this.userProfileRepository = userProfileRepository;
		this.actLogRepo = actLogRepo;
		this.exerciseRepo = exerRepo;
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

		Optional<Exercise> exerciseOptional = exerciseRepo.findById(exeId);
		if (exerciseOptional.isEmpty()){
			throw new NotFoundException("Can not found the corresponding exercise");
		}

		try{
			final var exercise = exerciseOptional.get();
			exercise.getFavoriteUser().add(userProfile);
			userProfileRepository.save(userProfile);
			exerciseRepo.save(exercise);
			return new ResponseObject(
					HttpStatus.OK.value(),
					"Add favorite exercise successfully",
					null);
		}
		catch (Exception e){
			throw new BadRequestException(e.getMessage());
		}
	}

	@Override
	public ResponseObject addWorkoutPlan(Long id) {
		return null;
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
//		saveUserProfile.setFavoriteExercises(exerciseRepository.findAll().subList(0,10));
//		final UserProfile saveUserFavorite = userProfileRepository.save(saveUserProfile);
//		savedUser.setUserProfile(saveUserProfile);
//		final User finalUser = repository.save(savedUser);
		return userMapper.userDTO(savedUser);
	}

}

