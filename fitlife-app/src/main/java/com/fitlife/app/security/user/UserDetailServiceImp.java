package com.fitlife.app.security.user;

import java.util.Optional;

import com.fitlife.app.exceptions.appException.NotFoundException;
import com.fitlife.app.model.user.User;
import com.fitlife.app.repository.jpa.user.UserRepository;
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
		Optional<User> result = userRepo.findByUsername(username);
		if (result.isEmpty()) {
			throw new NotFoundException("Can not found any user have name " + username);
		}
		return new FitLifeUserDetail(result.get());
	}

}
