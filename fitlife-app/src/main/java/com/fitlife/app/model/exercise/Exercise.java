package com.fitlife.app.model.exercise;

import java.util.List;

import com.fitlife.app.dataClass.dto.InstructionsDeserialize;
import com.fitlife.app.dataClass.views.ExerciseViews;
import com.fitlife.app.dataClass.views.SessionViews;
import com.fitlife.app.dataClass.views.UserViews;
import com.fitlife.app.model.session.Session;
import com.fitlife.app.model.user.UserProfile;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreType
@JsonIgnoreProperties(value = {"secondaryMuscles"})
public class Exercise {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This indicates auto-generation of IDs
	@JsonView(value = {ExerciseViews.Summary.class, SessionViews.Summary.class, UserViews.Summary.class,})
	private Long id;

	@JsonView(value = {ExerciseViews.Summary.class, SessionViews.Summary.class, UserViews.Summary.class,})
	private String name;

	@JsonView(value = {ExerciseViews.Summary.class, SessionViews.Summary.class, UserViews.Summary.class,})
	private String description;

	@JsonView(value = {ExerciseViews.Summary.class, SessionViews.Summary.class, UserViews.Summary.class,})
	private String exerciseCategory;

	@JsonView(value = {ExerciseViews.Detail.class})
	private double caloriesPerMinute;

	@JsonView(value = {ExerciseViews.Detail.class})
	private int sets;

	@JsonView(value = {ExerciseViews.Detail.class})
	private int reps;
	//
	@JsonView(value = {ExerciseViews.Summary.class})
	@JsonProperty("target")
	private String target;

	@JsonView(value = {ExerciseViews.Summary.class, UserViews.Summary.class,})
	@JsonProperty("gifUrl")
	private String gifUrl;

	@JsonView(value = {ExerciseViews.Detail.class})
	@JsonProperty("equipment")
	private String equipment;

	@JsonView(value = {ExerciseViews.Summary.class})
	@JsonProperty("bodyPart")
	private String bodyPart;

	@JsonView(value = {ExerciseViews.Detail.class})
	@JsonProperty("instructions")
	@JsonDeserialize(using = InstructionsDeserialize.class)
	@OneToMany(mappedBy = "exercise",orphanRemoval = true)
	private List<Steps> steps;

	@ManyToMany(
		fetch = FetchType.LAZY,
		cascade = {CascadeType.PERSIST,CascadeType.MERGE}
	)
	@JoinTable(
		name = "exercise_favorite",
		joinColumns = @JoinColumn(name = "exercise_id",referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "user_profile_id",referencedColumnName = "id")
	)
	private List<UserProfile> favoriteUser;

	@JsonView(ExerciseViews.Hide.class)
	@ManyToMany(
		fetch = FetchType.LAZY,
		cascade = {CascadeType.PERSIST,CascadeType.MERGE}
	)
	@JoinTable(
		name = "exercise_session",
		joinColumns = @JoinColumn(name = "exercise_id",referencedColumnName = "id"),
		inverseJoinColumns = @JoinColumn(name = "session_id",referencedColumnName = "id")
	)
	private List<Session> sessions;
	@JsonView(ExerciseViews.Hide.class)
	@OneToMany(
			fetch = FetchType.LAZY,
			mappedBy = "exercise",
			cascade = {CascadeType.PERSIST,CascadeType.MERGE}
	)
	private List<CustomExercise> customExercises;

}
