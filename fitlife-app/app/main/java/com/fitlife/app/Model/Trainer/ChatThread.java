package com.fitlife.app.Model.Trainer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Entity
public class ChatThread {
    @Id
    public String id;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(
            name = "trainer_id",
            referencedColumnName = "id"
    )
    public Trainer trainer;

    @OneToMany(
        mappedBy = "thread",
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    public List<Chat> chats = new ArrayList<>();
}
