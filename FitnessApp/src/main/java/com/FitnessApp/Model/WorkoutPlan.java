package com.FitnessApp.Model;

import java.util.List;

import com.FitnessApp.DTO.Views.ActivitiesLogViews;
import com.FitnessApp.DTO.Views.UserViews;
import com.FitnessApp.DTO.Views.WorkoutPlanViews;
import com.FitnessApp.Model.User.UserProfile;
import com.FitnessApp.Utils.Enums.PlanType;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class WorkoutPlan {

	@JsonView(value = {WorkoutPlanViews.Summary.class, UserViews.Summary.class, ActivitiesLogViews.Summary.class})
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This indica
	private Long id;

	@JsonView(value = {WorkoutPlanViews.Summary.class, UserViews.Summary.class,ActivitiesLogViews.Summary.class})
	private String name;

	@JsonView(value = {WorkoutPlanViews.Summary.class, UserViews.Summary.class})
	private String description;

	@JsonView(value = {WorkoutPlanViews.Summary.class, UserViews.Summary.class})
	private Long startDate;

	@JsonView(value = {WorkoutPlanViews.Summary.class, UserViews.Summary.class})
	private Long endDate;

	@JsonView(value = {WorkoutPlanViews.Summary.class, UserViews.Summary.class,ActivitiesLogViews.Summary.class})
	@Enumerated(EnumType.STRING)
	private PlanType type;

	@JsonView(value = {WorkoutPlanViews.Detail.class})
	@OneToMany(
		mappedBy = "workoutPlan",
		orphanRemoval = true,
		cascade = CascadeType.ALL
	)
	private List<DailyWorkout> dailyWorkouts;

	@OneToMany(
		mappedBy = "workoutPlan",
		orphanRemoval = true,
		cascade = CascadeType.ALL
	)
	private List<ActivitiesLog> activitiesLogs;

	@ManyToOne
	@JoinColumn(name = "user_profile_id", referencedColumnName = "id")
	private UserProfile userProfile;
}
