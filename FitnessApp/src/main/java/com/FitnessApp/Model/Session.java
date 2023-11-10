package com.FitnessApp.Model;

import com.FitnessApp.DTO.Views.SessionViews;
import com.FitnessApp.Model.Exercise.Exercise;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Session {
    @JsonView(value = {SessionViews.Summary.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "daily_workout_id",referencedColumnName = "id")
    private DailyWorkout dailyWorkouts;

    @JsonView(value = {SessionViews.Summary.class})
    @ManyToMany(
        mappedBy = "sessions",
        fetch = FetchType.EAGER,
        cascade = {CascadeType.PERSIST,CascadeType.MERGE}
    )
    private List<Exercise> exercises;
}
