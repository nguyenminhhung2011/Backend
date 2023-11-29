package com.FitnessApp.DTO.Request;

public class SessionDTOReq {
	private Long dailyWorkouts;

	private Boolean startWithBoot;
	private Boolean randomMix;
	private int timePerLesson;
	private int transferTime;
	private String description;
	private String name;

	// Constructors, getters, setters, and other methods can be added here

	// Constructor
	public SessionDTOReq() {

	}

	public SessionDTOReq(Long dailyWorkouts, Boolean startWithBoot, Boolean randomMix, int timePerLesson,
			int transferTime, String description, String name) {
		this.dailyWorkouts = dailyWorkouts;
		this.startWithBoot = startWithBoot;
		this.randomMix = randomMix;
		this.timePerLesson = timePerLesson;
		this.transferTime = transferTime;
		this.description = description;
		this.name = name;
	}

	// Getters and setters
	public Long getDailyWorkouts() {
		return dailyWorkouts;
	}

	public void setDailyWorkouts(Long dailyWorkouts) {
		this.dailyWorkouts = dailyWorkouts;
	}

	public Boolean getStartWithBoot() {
		return startWithBoot;
	}

	public void setStartWithBoot(Boolean startWithBoot) {
		this.startWithBoot = startWithBoot;
	}

	public Boolean getRandomMix() {
		return randomMix;
	}

	public void setRandomMix(Boolean randomMix) {
		this.randomMix = randomMix;
	}

	public int getTimePerLesson() {
		return timePerLesson;
	}

	public void setTimePerLesson(int timePerLesson) {
		this.timePerLesson = timePerLesson;
	}

	public int getTransferTime() {
		return transferTime;
	}

	public void setTransferTime(int transferTime) {
		this.transferTime = transferTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
