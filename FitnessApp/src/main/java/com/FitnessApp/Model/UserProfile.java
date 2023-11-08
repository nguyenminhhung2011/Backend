package com.FitnessApp.Model;

import java.sql.Date;
import java.util.List;

import com.FitnessApp.Enums.Frequency;
import com.FitnessApp.Enums.Gender;
import com.FitnessApp.Enums.ThemeStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

//	@Temporal(TemporalType.TIMESTAMP)
//	private Date birthday;
//

	private double weight = 0;
	private double height = 0;
	private String phone = "";
	private int level = 0;
	private String currentPlan; // Current workout plan that working on

	@Enumerated(EnumType.STRING)
	private ThemeStatus themeStatus = ThemeStatus.LIGHT;

	@Enumerated(EnumType.STRING)
	private Gender gender;

	@Enumerated(EnumType.STRING)
	private Frequency frequency;

	@OneToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	private User user;

	@OneToMany(
		mappedBy = "userProfile",
		cascade = CascadeType.ALL,
		orphanRemoval = true
)
	private List<ActivitiesLog> activitiesLogs; //History

	@ManyToMany(
		mappedBy = "favoriteUser",
		cascade = {CascadeType.PERSIST,CascadeType.MERGE}
	)
	private List<Exercise> favoriteExercises;

	@OneToMany(
			mappedBy = "userProfile",
			cascade = {CascadeType.ALL},
			orphanRemoval = true
	)
	private List<WorkoutPlan> workoutPlans;
}
