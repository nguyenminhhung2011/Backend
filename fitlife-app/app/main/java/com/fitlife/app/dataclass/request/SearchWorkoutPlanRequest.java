package com.fitlife.app.dataclass.request;


public record SearchWorkoutPlanRequest(String name,int page,int size,Long startDate,Long endDate) { }
