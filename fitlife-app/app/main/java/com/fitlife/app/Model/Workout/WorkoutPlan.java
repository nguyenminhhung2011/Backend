package com.fitlife.app.Model.Workout;

import java.util.List;

import com.fitlife.app.DTO.Views.ActivitiesLogViews;
import com.fitlife.app.DTO.Views.UserViews;
import com.fitlife.app.DTO.Views.WorkoutPlanViews;
import com.fitlife.app.Model.AISupport;
import com.fitlife.app.Model.ActivitiesLog;
import com.fitlife.app.Model.User.UserProfile;
import com.fitlife.app.Utils.Enums.PlanType;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.*;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class WorkoutPlan {

	@JsonView(value = { WorkoutPlanViews.Summary.class, UserViews.Summary.class, ActivitiesLogViews.Summary.class })
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@JsonView(value = { WorkoutPlanViews.Summary.class, UserViews.Summary.class, ActivitiesLogViews.Summary.class })
	private String name;

	@JsonView(value = { WorkoutPlanViews.Summary.class, UserViews.Summary.class })
	private String description;

	@JsonView(value = { WorkoutPlanViews.Summary.class, UserViews.Summary.class })
	private long  startDate;

	@JsonView(value = { WorkoutPlanViews.Summary.class, UserViews.Summary.class })
	private long endDate;

	@JsonView(value = { WorkoutPlanViews.Summary.class, UserViews.Summary.class, ActivitiesLogViews.Summary.class })
	@Enumerated(EnumType.STRING)
	private PlanType type;

	@JsonView(value = { WorkoutPlanViews.Detail.class })
	@OneToMany(mappedBy = "workoutPlan", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<DailyWorkout> dailyWorkouts;

	@OneToMany(mappedBy = "workoutPlan", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<ActivitiesLog> activitiesLogs;

	@ManyToOne
	@JoinColumn(name = "user_profile_id", referencedColumnName = "id")
	private UserProfile userProfile;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "workoutplan")
	private AISupport aiSupport;

}
