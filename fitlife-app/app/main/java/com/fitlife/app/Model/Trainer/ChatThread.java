package com.fitlife.app.Model.Trainer;

import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fitlife.app.Model.User.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.UUIDGenerator;
import org.hibernate.id.uuid.UuidGenerator;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Builder
public class ChatThread {
    @Id
    @GenericGenerator(name = "system-uuid", type = UuidGenerator.class)
    public String id;

    public String title;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "trainer_id", referencedColumnName = "id")
    public Trainer trainer;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    public User user;

    @OneToMany(
        mappedBy = "thread",
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    public List<Chat> chats = new ArrayList<>();

}
