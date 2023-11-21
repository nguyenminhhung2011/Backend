package com.FitnessApp.Service.User;

import java.util.List;
import java.util.Optional;

import com.FitnessApp.DTO.DataClass.User.UserDTO;
import com.FitnessApp.DTO.DataClass.User.UserProfileDTO;
import com.FitnessApp.DTO.Request.RegistrationRequest;
import com.FitnessApp.Exceptions.AppException.NotFoundException;
import com.FitnessApp.Mapper.UserMapper;
import com.FitnessApp.Model.UserProfile;
import com.FitnessApp.Repository.UserProfileRepository;
import com.FitnessApp.Service.Generic.GenericService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.FitnessApp.Model.User;
import com.FitnessApp.Repository.UserRepository;

@Service
public class UserServiceImpl extends GenericService<User,Long,UserRepository> implements IUserService {
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	private final UserProfileRepository userProfileRepository;
	public UserServiceImpl(UserRepository genericRepository, UserMapper userMapper, PasswordEncoder passwordEncoder, UserProfileRepository userProfileRepository) {
		super(genericRepository);
		this.userMapper = userMapper;
		this.passwordEncoder = passwordEncoder;
		this.userProfileRepository = userProfileRepository;
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

	public UserDTO getUserByUsername(String username) {
		Optional<User> result = repository.findByUsername(username);
		if (result.isEmpty()) {
			throw new NotFoundException("Can not found any user have name " + username);
		}
		return userMapper.userDTO(result.get());
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

