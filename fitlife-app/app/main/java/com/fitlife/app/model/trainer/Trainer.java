package com.fitlife.app.model.trainer;

import java.util.List;
import java.util.UUID;

import com.fitlife.app.model.user.User;
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
    public String description;
    public String image;
    public String greetingMessage;
    public String bio;

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
