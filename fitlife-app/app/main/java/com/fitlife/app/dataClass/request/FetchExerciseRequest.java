package com.fitlife.app.dataClass.request;

public record FetchExerciseRequest(String search,String name,String bodyPart,String target,PageRequest pageRequest) {}
