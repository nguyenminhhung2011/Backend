package com.fitlife.app.Model;

import java.util.List;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DailyWorkout {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This indicates auto-generation of IDs

	private Long id;

	private String name;
	private String description;
	private double caloTarget;
	private Long time; // Thời gian trong phút (1h = 60 phút)
	private int workoutDuration;
	private int numberRound;
	private int execPerRound;
	private int timeForEachExe;
	private int breakTime;

	@ManyToOne
	@JoinColumn(name = "workout_plan_id",referencedColumnName = "id")
	private WorkoutPlan workoutPlan;

	@OneToMany(
			mappedBy = "dailyWorkouts",
			orphanRemoval = true,
			cascade = CascadeType.ALL
	)
	private List<Session> sessions;
}
