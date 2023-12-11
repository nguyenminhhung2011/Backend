package com.fitlife.app.Model.Exercise;

import java.sql.Time;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import com.fitlife.app.Model.session.Session;
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

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date dateStart;

	@JsonFormat(pattern = "HH:mm:ss")
	private Time time;
	private String difficulty;
	private int rep;
	private int weight;

	public CustomExercise() {}

	@ManyToOne
	private Session session;

	@ManyToOne
	@JoinColumn(name = "exercise_id", referencedColumnName = "id")
	private Exercise exercise;


}
