package com.fitlife.app.dataClass.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DailyWorkoutRequest {
	private Long id;
	private String name;
	private String description;
	private Long time;

}