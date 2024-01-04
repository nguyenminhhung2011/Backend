package com.fitlife.app.Model.Trainer;

import java.util.List;
import java.util.UUID;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.uuid.UuidGenerator;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Trainer {
    @Id
    @GenericGenerator(name = "system-uuid", type = UuidGenerator.class)
    public String id;
    public String name;
    public String model;
    public String userId;
    @OneToMany(
        mappedBy = "trainer",
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    public List<ChatThread> threads;
}
