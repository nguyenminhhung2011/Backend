package com.FitnessApp.Model;

import java.sql.Time;
import java.util.Date;

import com.FitnessApp.Model.Exercise.Exercise;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
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

	public CustomExercise() {

	}

	@ManyToOne
	private Session session;

	@ManyToOne
	private Exercise exercise;
	// Constructors, getters, setters, and other methods can be added here
	// Constructor

	public Session getSession() {
		return null;
	}

	public Long getExercise() {
		return exercise.getId();
	}

}
