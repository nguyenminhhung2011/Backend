package com.FitnessApp.Service.User;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.FitnessApp.DTO.DataClass.ResponseObject;
import com.FitnessApp.DTO.DataClass.User.UserDTO;
import com.FitnessApp.DTO.DataClass.User.UserProfileDTO;
import com.FitnessApp.DTO.Request.RegistrationRequest;
import com.FitnessApp.Exceptions.AppException.BadRequestException;
import com.FitnessApp.Exceptions.AppException.NotFoundException;
import com.FitnessApp.Mapper.UserMapper;
import com.FitnessApp.Model.User;
import com.FitnessApp.Model.UserProfile;
import com.FitnessApp.Model.Exercise.Exercise;
import com.FitnessApp.Repository.ExerciseRepository;
import com.FitnessApp.Repository.UserProfileRepository;
import com.FitnessApp.Repository.UserRepository;
import com.FitnessApp.Repository.WorkoutRepository;
import com.FitnessApp.Service.Generic.GenericService;

@Service
public class UserServiceImpl extends GenericService<User, Long, UserRepository> implements IUserService {
	private final UserMapper userMapper;

	private final PasswordEncoder passwordEncoder;
	private final UserProfileRepository userProfileRepository;

	private final ExerciseRepository exerRepo;
	private final WorkoutRepository workoutRepository;

	public UserServiceImpl(UserRepository genericRepository, UserMapper userMapper, PasswordEncoder passwordEncoder,
			UserProfileRepository userProfileRepository, ExerciseRepository exerRepo,
			WorkoutRepository workoutRepository) {
		super(genericRepository);
		this.userMapper = userMapper;

		this.passwordEncoder = passwordEncoder;
		this.userProfileRepository = userProfileRepository;

		this.exerRepo = exerRepo;
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
	public UserProfileDTO getUserProfile(Long id) {
		final Optional<UserProfile> userProfile = userProfileRepository.findById(id);
		if (userProfile.isEmpty()) {
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
	public UserDTO updateUserProfile(Long id, UserDTO userDTO) {
		User user = findById(id);
		UserProfile userProfile = user.getUserProfile();
		UserProfileDTO newUserProfileDTO = userDTO.getUserProfile();

		userProfile.setFrequency(newUserProfileDTO.getFrequency());
		userProfile.setGender(newUserProfileDTO.getGender());
		userProfile.setHeight(newUserProfileDTO.getHeight());
		userProfile.setCurrentPlan(newUserProfileDTO.getCurrentPlan());
		userProfile.setPhone(newUserProfileDTO.getPhone());

		userProfileRepository.save(userProfile);

		return userMapper.userDTO(user);
	}

	@Override
	public ResponseObject addFavoriteExercise(Long userId, Long exeId) throws BadRequestException {

		User user = findById(userId);
		UserProfile userProfile = user.getUserProfile();

		Optional<Exercise> exerciseOptional = exerRepo.findById(exeId);
		if (exerciseOptional.isEmpty()) {
			throw new NotFoundException("Can not found the corresponding exercise");
		}

		try {
			final var exercise = exerciseOptional.get();
//			final var favoriteExercises = userProfile.getFavoriteExercises();

//			if (favoriteExercises.contains(exercise)){
//				return new ResponseObject(HttpStatus.OK.value(),"Success",null);
//			}

//			favoriteExercises.add(exercise);
			exercise.getFavoriteUser().add(userProfile);

			userProfileRepository.save(userProfile);
			exerRepo.save(exercise);
			return new ResponseObject(HttpStatus.OK.value(), "Add favorite exercise successfully", null);
		} catch (Exception e) {
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
		User newUser = new User(null, request.getUsername(), passwordEncoder.encode(request.getPassword()), null, null);
		final User savedUser = repository.save(newUser);

		UserProfile userProfile = UserProfile.builder().id(null).user(savedUser).build();
		final UserProfile saveUserProfile = userProfileRepository.save(userProfile);
		savedUser.setUserProfile(saveUserProfile);
//		saveUserProfile.setFavoriteExercises(exerciseRepository.findAll().subList(0,10));
//		final UserProfile saveUserFavorite = userProfileRepository.save(saveUserProfile);
//		savedUser.setUserProfile(saveUserProfile);
//		final User finalUser = repository.save(savedUser);
		return userMapper.userDTO(savedUser);
	}

}
