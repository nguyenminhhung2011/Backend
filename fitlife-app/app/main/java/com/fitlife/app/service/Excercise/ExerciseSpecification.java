package com.fitlife.app.service.Excercise;

import com.fitlife.app.dataclass.request.FetchExerciseRequest;
import com.fitlife.app.model.Exercise.Exercise;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.domain.Specification;

public class ExerciseSpecification implements  Specification<Exercise> {

    private final FetchExerciseRequest request;

    public ExerciseSpecification(FetchExerciseRequest request) {
        this.request = request;
    }

    @Override
    public Predicate toPredicate(@NotNull Root<Exercise> root, @NotNull CriteriaQuery<?> query, @NotNull CriteriaBuilder criteriaBuilder) {
        return null;
    }

    public static Specification<Exercise> hasExerciseName(String name) {
        return (exercise, cq, cb) -> cb.equal(exercise.get("name"), name);
    }

    public static Specification<Exercise> hasExerciseS(String name) {
        return (exercise, cq, cb) -> cb.equal(exercise.get("name"), name);
    }

}
