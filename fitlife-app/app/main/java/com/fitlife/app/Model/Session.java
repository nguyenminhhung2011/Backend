package com.fitlife.app.Model;

import java.util.List;

import com.fitlife.app.DTO.Views.SessionViews;
import com.fasterxml.jackson.annotation.JsonView;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity

@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
	@JsonView(value = { SessionViews.Summary.class })
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ManyToOne(optional = false)
	@JoinColumn(name = "daily_workout_id", referencedColumnName = "id")
	private DailyWorkout dailyWorkouts;

	@JsonView(value = { SessionViews.Summary.class })
	@OneToMany(mappedBy = "session", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private List<CustomExercise> customExercise;

	private Boolean startWithBoot;
	private Boolean randomMix;
	private int timePerLesson;
	private int transferTime;
	private String description;
	private String name;

	public DailyWorkout getDailyWorkouts() {
		return null;
	}

	public List<CustomExercise> getCustomExercise() {
		return customExercise;
	}

	public Long getId() {
		return id;
	}

	public Boolean getStartWithBoot() {
		return startWithBoot;
	}

	public Boolean getRandomMix() {
		return randomMix;
	}

	public int getTimePerLesson() {
		return timePerLesson;
	}

	public int getTransferTime() {
		return transferTime;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return name;
	}

}
