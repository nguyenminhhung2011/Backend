package com.fitlife.app.DTO.Request;


public record SearchWorkoutPlanRequest(String name,int page,int size,Long startDate,Long endDate) { }
