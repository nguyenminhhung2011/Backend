package com.fitlife.app.dataClass.request;


public record SearchWorkoutPlanRequest(String name,int page,int size,Long startDate,Long endDate) { }
