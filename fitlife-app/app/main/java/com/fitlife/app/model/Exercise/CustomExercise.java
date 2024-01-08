package com.fitlife.app.model.Exercise;

import com.fitlife.app.model.Session.Session;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@SuperBuilder
@Setter
@Getter
public class CustomExercise {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String difficulty;
	private int rep;
	private int time;
	private int weight;

	private int calories;

	public CustomExercise() {}

	@ManyToOne
	private Session session;

	@ManyToOne
	@JoinColumn(name = "exercise_id", referencedColumnName = "id")
	private Exercise exercise;


}
