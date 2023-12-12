package com.fitlife.app.Model.session;

import java.util.List;

import com.fitlife.app.DTO.Views.SessionViews;
import com.fasterxml.jackson.annotation.JsonView;

import com.fitlife.app.Model.Exercise.CustomExercise;
import com.fitlife.app.Model.Workout.DailyWorkout;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.*;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
	@JsonView(value = { SessionViews.Summary.class })
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String name;
	private String level;
	private String description;
	private Boolean startWithBoot;
	private Boolean randomMix;

	private int numberRound;
	private int breakTime;
	private int timePerLesson;
	private int transferTime;
	private int calcTarget;
	@ManyToOne(optional = false)
	@JoinColumn(name = "daily_workout_id", referencedColumnName = "id")
	private DailyWorkout dailyWorkouts;

	@JsonView(value = { SessionViews.Summary.class })
	@OneToMany(mappedBy = "session", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<CustomExercise> customExercise;
}
