package com.FitnessApp.Model;

import com.FitnessApp.Security.Model.User;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Builder;

@Entity
//@Data
@Builder
public class Gymer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	private UserProfile profile;
	private String fullName;
	@OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private User user;
	private int age;
	private String gender;

//	public Gymer(Long id, Long profileId, String username, String password, int age, String gender) {
//		super();
//		this.id = id;
//		this.profileId = profileId;
//		this.fullName = username;
//
//		this.age = age;
//		this.gender = gender;
//	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public UserProfile getProfileId() {
		return profile;
	}

	public void setProfileId(UserProfile profileId) {
		this.profile = profileId;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public void setFullname(String username) {
		this.fullName = username;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

}
