package com.fitlife.app.model.Trainer;

import java.util.List;
import java.util.UUID;

import com.fitlife.app.model.User.User;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
@Builder
public class Trainer {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;
    public String name;
    public String model;

    @ManyToOne
    @JoinColumn(
        name = "user_id",
        referencedColumnName = "userId"
    )
    public User user;

    @OneToMany(
        mappedBy = "trainer",
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    public List<ChatThread> threads;
}
