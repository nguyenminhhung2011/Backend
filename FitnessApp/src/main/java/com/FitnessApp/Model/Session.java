package com.FitnessApp.Model;

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
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "daily_workout_id",referencedColumnName = "id")
    private DailyWorkout dailyWorkouts;

    @ManyToMany(
        mappedBy = "sessions",
        fetch = FetchType.EAGER,
        cascade = {CascadeType.PERSIST,CascadeType.MERGE}
    )
    private List<Exercise> exercises;
}
