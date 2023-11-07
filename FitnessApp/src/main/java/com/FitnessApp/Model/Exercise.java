package com.FitnessApp.Model;

import java.util.List;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Exercise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This indicates auto-generation of IDs
	private Long id;
	private String name;
	private String description;
	private String exerciseCategory;
	private double caloriesPerMinute;
	private String videoUrl;
	private int sets;
	private int reps;

	@OneToMany(mappedBy = "exercise")
	private List<Steps> steps;

	@ManyToMany
	@JoinTable(
			name = "exercise_favorite",
			joinColumns = @JoinColumn(name = "exercise_id",referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "user_profile_id",referencedColumnName = "id")
	)
	private List<UserProfile> favoriteUser;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(
			name = "exercise_session",
			joinColumns = @JoinColumn(name = "exercise_id",referencedColumnName = "id"),
			inverseJoinColumns = @JoinColumn(name = "session_id",referencedColumnName = "id")
	)

	private List<Session> sessions;

}
