package com.FitnessApp.Service.User;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.FitnessApp.Model.User;
import com.FitnessApp.Repository.UserRepository;

@Service
@AllArgsConstructor
public class UserService implements IUserService {
	private final UserRepository repository;

	private final PasswordEncoder passEncoder;

	public Optional<User> findById(Long id) {
		return repository.findById(id);
	}

	public List<User> findByUsername(String username) {
		List<User> result = repository.findByUsername(username);
		if (result == null) {
			result = Collections.emptyList();
			return result;
		}
		return result;
	}

	public User findOneByUsername(String username) {
		List<User> result = repository.findByUsername(username);
		if (result == null) {
			return null;
		}
		return result.get(0);
	}

	public List<User> findAll() {
		return repository.findAll().stream().toList();
	}

	public User create(User user) {
		String pass;
		pass = passEncoder.encode(user.getPassword());
		user.setPassword(pass);
		return repository.save(user);
	}

	public User save(User user) {
		return repository.save(user);
	}

	public void deleteById(long id) {
		repository.deleteById(id);
	}

	public void deleteAll() {
		repository.deleteAll();
	}
}
