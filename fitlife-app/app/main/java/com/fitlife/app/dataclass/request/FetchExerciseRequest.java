package com.fitlife.app.DTO.Request;

public record FetchExerciseRequest(String search,String name,String bodyPart,String target,PageRequest pageRequest) {}
