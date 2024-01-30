package com.fitlife.app.model.workout;

import java.util.List;

import com.fitlife.app.dataClass.views.ActivitiesLogViews;
import com.fitlife.app.dataClass.views.UserViews;
import com.fitlife.app.dataClass.views.WorkoutPlanViews;
import com.fitlife.app.model.AISupport;
import com.fitlife.app.model.ActivitiesLog;
import com.fitlife.app.model.user.UserProfile;
import com.fitlife.app.utils.enums.PlanType;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.*;
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
