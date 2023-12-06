package com.fitlife.app.DTO.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyWorkoutReq {
	private Long id;
	private String name;
	private String description;
	private double caloTarget;
	private Long time;
	private int workoutDuration;
	private int numberRound;
	private int execPerRound;
	private int timeForEachExe;
	private int breakTime;

}