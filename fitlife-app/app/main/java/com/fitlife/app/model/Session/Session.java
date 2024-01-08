package com.fitlife.app.model.Session;

import java.util.List;

import com.fitlife.app.dataclass.views.SessionViews;
import com.fasterxml.jackson.annotation.JsonView;

import com.fitlife.app.model.Exercise.CustomExercise;
import com.fitlife.app.model.Workout.DailyWorkout;
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
	private Boolean done;

	private int numberRound;
	private int breakTime;
	private int timePerLesson;
	private int transferTime;
	private int calcTarget;
	private int calcCompleted;
	@ManyToOne(optional = false)
	@JoinColumn(name = "daily_workout_id", referencedColumnName = "id")
	private DailyWorkout dailyWorkouts;

	@JsonView(value = { SessionViews.Summary.class })
	@OneToMany(mappedBy = "session", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<CustomExercise> customExercise;
}
