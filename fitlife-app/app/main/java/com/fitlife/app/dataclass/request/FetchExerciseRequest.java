package com.fitlife.app.dataclass.request;

public record FetchExerciseRequest(String search,String name,String bodyPart,String target,PageRequest pageRequest) {}
