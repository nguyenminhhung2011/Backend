package com.fitlife.app.DTO.Request;

import lombok.Data;

import java.sql.Time;
import java.util.Date;

@Data
public class CustomExerciseRequest {
	private Date dateStart;
	private Time time;
	private String difficulty;
	private int rep;
	private int weight;
	private Long exercise;

}
