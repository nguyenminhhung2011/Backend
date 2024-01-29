package com.fitlife.app.model.user;

import java.sql.Timestamp;
import java.util.List;
import com.fitlife.app.model.ActivitiesLog;
import com.fitlife.app.model.newsHealth.NewsHealth;
import com.fitlife.app.model.workout.WorkoutPlan;
import com.fitlife.app.utils.enums.Frequency;
import com.fitlife.app.utils.enums.Gender;
import com.fitlife.app.utils.enums.ThemeStatus;
import com.fitlife.app.dataClass.views.UserViews;
import com.fitlife.app.model.exercise.Exercise;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;
@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserProfile {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Builder.Default
	@Column(columnDefinition = "boolean default false")
	private boolean isCreated = false;

	@JsonView(value = {UserViews.Summary.class})
	private String fullName;

	@JsonView(value = {UserViews.Summary.class})
	private Timestamp birthday;

	@JsonView(value = {UserViews.Summary.class})
	@Builder.Default
	private String phone = "";

	@JsonView(value = {UserViews.Summary.class})
	private String currentPlan; // Current workout plan that working on

	@JsonView(value = {UserViews.Summary.class})
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@JsonView(value = {UserViews.Detail.class})
	@Builder.Default
	private double weight = 0;

	@JsonView(value = {UserViews.Detail.class})
	@Builder.Default
	private double height = 0;

	@JsonView(value = {UserViews.Detail.class})
	private Long currentPlanId;

	@JsonView(value = {UserViews.Detail.class})
	@Enumerated(EnumType.STRING)
	@Builder.Default
	private ThemeStatus themeStatus = ThemeStatus.LIGHT;

	@JsonView(value = {UserViews.Detail.class})
	@Enumerated(EnumType.STRING)
	private Frequency frequency;

	@OneToOne(fetch = FetchType.LAZY,optional = false)
	@JoinColumn(name = "userId", referencedColumnName = "userId")
	@JsonIgnore
	private User user;

	@JsonView(value = {UserViews.Detail.class})
	@OneToMany(
		mappedBy = "userProfile",
		cascade = CascadeType.ALL,
		orphanRemoval = true
	)
	private List<ActivitiesLog> activitiesLogs;

	@JsonView(value = {UserViews.Detail.class})
	@ManyToMany(
		mappedBy = "favoriteUser",
		cascade = {CascadeType.PERSIST,CascadeType.MERGE}
	)
	private List<Exercise> favoriteExercises;

	@JsonView(value = {UserViews.Detail.class})
	@ManyToMany(
		mappedBy = "newsUser",
		cascade = {CascadeType.PERSIST,CascadeType.MERGE}
	)
	private List<NewsHealth> favoriteNews;

	@JsonView(value = {UserViews.Detail.class})
	@OneToMany(
			mappedBy = "userProfile",
			cascade = {CascadeType.ALL},
			orphanRemoval = true
	)
	private List<WorkoutPlan> workoutPlans;
}
