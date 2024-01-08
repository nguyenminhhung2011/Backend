package com.fitlife.app.repository.jpa.user;

import java.util.Optional;

import com.fitlife.app.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long>{
	Optional<User> findByUsername(String username);
}
