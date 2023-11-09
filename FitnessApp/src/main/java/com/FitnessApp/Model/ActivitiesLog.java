package com.FitnessApp.Model;

import com.FitnessApp.DTO.Views.ActivitiesLogViews;
import com.FitnessApp.DTO.Views.UserViews;
import com.fasterxml.jackson.annotation.JsonView;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class ActivitiesLog {
    @JsonView(value = {ActivitiesLogViews.Summary.class, UserViews.Summary.class})
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(value = {ActivitiesLogViews.Summary.class, UserViews.Summary.class})
    private Long time;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_profile_id",referencedColumnName = "id")
    private UserProfile userProfile;

    @JsonView(value = {ActivitiesLogViews.Summary.class})
    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(
            name = "workout_plan_id",
            referencedColumnName = "id"
    )
    private WorkoutPlan workoutPlan;
}
