package com.FitnessApp.Service.ProfileService;

import org.springframework.stereotype.Service;

import com.FitnessApp.Model.UserProfile;
import com.FitnessApp.Repository.UserProfileRepository;
import com.FitnessApp.Service.Generic.GenericService;

@Service
public class ProfileServiceImpl extends GenericService<UserProfile, Long, UserProfileRepository>
		implements IUserProfileService {

	public ProfileServiceImpl(UserProfileRepository repository) {
		super(repository);
		// TODO Auto-generated constructor stub
	}

}
