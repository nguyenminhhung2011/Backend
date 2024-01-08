package com.fitlife.app.model;

import com.fitlife.app.dataclass.views.ActivitiesLogViews;
import com.fitlife.app.dataclass.views.UserViews;
import com.fitlife.app.model.User.UserProfile;
import com.fasterxml.jackson.annotation.JsonView;
import com.fitlife.app.model.Workout.WorkoutPlan;
import jakarta.persistence.*;
import lombok.*;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
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
