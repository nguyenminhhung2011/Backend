package com.FitnessApp.Repository.User;

import com.FitnessApp.Model.User.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserProfileRepository extends JpaRepository<UserProfile,Long> {}
