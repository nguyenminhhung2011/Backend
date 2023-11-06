package com.FitnessApp.Security;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.FitnessApp.Model.Role;
import com.FitnessApp.Repository.RoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

	private final RoleRepository roleRepository;

	public DataInitializer(RoleRepository roleRepository) {
		this.roleRepository = roleRepository;
	}

	@Override
	public void run(String... args) {

		if (roleRepository.count() == 0) {

			Role role_first = new Role((long) 1, "CLIENT");
			Role role_second = new Role((long) 2, "ADMIN");
			roleRepository.save(role_first);
			roleRepository.save(role_second);
		}
	}
}
