package com.fitlife.app.DTO.Request;

import java.sql.Time;
import java.util.Date;

public class CustomExerciseDTO {
	private Date dateStart;
	private Time time;
	private String difficulty;
	private int rep;
	private int weight;
	private Long session;
	private Long exercise;

	// Constructors, getters, setters, and other methods can be added here

	public CustomExerciseDTO() {
		// Default constructor
	}

	public CustomExerciseDTO(Date dateStart, Time time, String difficulty, int rep, int weight, Long session,
			Long exercise) {
		this.dateStart = dateStart;
		this.time = time;
		this.difficulty = difficulty;
		this.rep = rep;
		this.weight = weight;
		this.session = session;
		this.exercise = exercise;
	}

	// Getters and setters
	public Date getDateStart() {
		return dateStart;
	}

	public void setDateStart(Date dateStart) {
		this.dateStart = dateStart;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	public String getDifficulty() {
		return difficulty;
	}

	public void setDifficulty(String difficulty) {
		this.difficulty = difficulty;
	}

	public int getRep() {
		return rep;
	}

	public void setRep(int rep) {
		this.rep = rep;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public Long getSession() {
		return session;
	}

	public void setSession(Long session) {
		this.session = session;
	}

	public Long getExercise() {
		return exercise;
	}

	public void setExercise(Long exercise) {
		this.exercise = exercise;
	}
}
