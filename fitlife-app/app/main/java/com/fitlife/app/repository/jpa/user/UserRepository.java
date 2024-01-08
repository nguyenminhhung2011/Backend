package com.fitlife.app.repository.jpa.user;

import java.util.Optional;

import com.fitlife.app.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
	@Query("select u from User u where u.trainers = ?1")
	Optional<User> findByTrainers(String trainerId);


}
