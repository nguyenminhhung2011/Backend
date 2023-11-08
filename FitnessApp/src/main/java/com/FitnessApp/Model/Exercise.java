package com.FitnessApp.Model;

import java.util.List;

import com.FitnessApp.DTO.InstructionsDeserialize;
import com.fasterxml.jackson.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(value = {"secondaryMuscles","id",})
public class Exercise {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) // This indicates auto-generation of IDs
	private Long id;
	private String name;
	private String description;
	private String exerciseCategory;
	private double caloriesPerMinute;
	private int sets;
	private int reps;
	//
	@JsonProperty("target")
	private String target;
	@JsonProperty("gifUrl")
	private String videoUrl;
	@JsonProperty("equipment")
	private String equipment;
	@JsonProperty("bodyPart")
	private String bodyPart;

	@JsonProperty("instructions")
	@JsonDeserialize(using = InstructionsDeserialize.class)
	@OneToMany(mappedBy = "exercise",cascade = CascadeType.ALL,orphanRemoval = true)
	private List<Steps> steps;
	//

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

}
