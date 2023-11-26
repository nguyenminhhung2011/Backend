package com.FitnessApp.Model.Exercise;

import java.util.List;

import com.FitnessApp.DTO.DataClass.InstructionsDeserialize;
import com.FitnessApp.DTO.Views.ExerciseViews;
import com.FitnessApp.DTO.Views.SessionViews;
import com.FitnessApp.DTO.Views.UserViews;
import com.FitnessApp.Model.CustomExercise;
import com.FitnessApp.Model.Steps;
import com.FitnessApp.Model.UserProfile;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIgnoreType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreType
@JsonIgnoreProperties(value = { "secondaryMuscles" })
public class Exercise {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This indicates auto-generation of IDs
	@JsonView(value = { ExerciseViews.Summary.class, SessionViews.Summary.class, UserViews.Summary.class, })
	private Long id;

	@JsonView(value = { ExerciseViews.Summary.class, SessionViews.Summary.class, UserViews.Summary.class, })
	private String name;

	@JsonView(value = { ExerciseViews.Summary.class, SessionViews.Summary.class, UserViews.Summary.class, })
	private String description;

	@JsonView(value = { ExerciseViews.Summary.class, SessionViews.Summary.class, UserViews.Summary.class, })
	private String exerciseCategory;

	@JsonView(value = { ExerciseViews.Detail.class })
	private double caloriesPerMinute;

	@JsonView(value = { ExerciseViews.Detail.class })
	private int sets;

	@JsonView(value = { ExerciseViews.Detail.class })
	private int reps;
	//
	@JsonView(value = { ExerciseViews.Summary.class })
	@JsonProperty("target")
	private String target;

	@JsonView(value = { ExerciseViews.Summary.class, UserViews.Summary.class, })
	@JsonProperty("gifUrl")
	private String videoUrl;

	@JsonView(value = { ExerciseViews.Detail.class })
	@JsonProperty("equipment")
	private String equipment;

	@JsonView(value = { ExerciseViews.Summary.class })
	@JsonProperty("bodyPart")
	private String bodyPart;

	@JsonView(value = { ExerciseViews.Detail.class })
	@JsonProperty("instructions")
	@JsonDeserialize(using = InstructionsDeserialize.class)
	@OneToMany(mappedBy = "exercise", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Steps> steps;

	@JsonView(ExerciseViews.Hide.class)
	@ManyToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE })
	@JoinTable(name = "exercise_favorite", joinColumns = @JoinColumn(name = "exercise_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "user_profile_id", referencedColumnName = "id"))
	private List<UserProfile> favoriteUser;

	@OneToMany(fetch = FetchType.LAZY, cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "exercise")
	private List<CustomExercise> customExercises;

	public CustomExercise getCustomExercises() {
		return null;
	}

}
