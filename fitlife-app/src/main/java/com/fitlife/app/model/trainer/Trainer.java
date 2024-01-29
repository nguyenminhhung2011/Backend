package com.fitlife.app.model.trainer;

import java.util.List;

import com.fitlife.app.model.user.User;
import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "trainer")
@Builder
public class Trainer {
    @Id
    @org.springframework.data.annotation.Id
//    @GeneratedValue(strategy = GenerationType.UUID)
    public String id;
    public String name;
    public String model;
    @Column(name = "prompt",length = 50000)
    public String prompt;
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
