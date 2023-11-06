package com.FitnessApp.Security.Model;

import java.util.List;

import com.FitnessApp.Model.User;
import com.FitnessApp.Repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class UserDetailServiceImp implements UserDetailsService{

	private final UserRepository userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		List<User> result = userRepo.findByUsername(username);
		if (result.size()>1) {
			throw(new UsernameNotFoundException("There are Many User"));
		}
		return new CustomUserDetail(result.get(0));
	}

}
