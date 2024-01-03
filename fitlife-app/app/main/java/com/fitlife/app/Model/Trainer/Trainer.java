package com.fitlife.app.Model.Trainer;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Trainer {
    @Id
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
