package com.FitnessApp.Model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ActivitiesLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long time;
    @ManyToOne(optional = false)
    @JoinColumn(name = "user_profile_id",referencedColumnName = "id")
    private UserProfile userProfile;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(
            name = "workout_plan_id",
            referencedColumnName = "id"
    )
    private WorkoutPlan workoutPlan;
}
