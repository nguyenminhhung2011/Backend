package com.fitlife.app.Model.Trainer;

import com.fitlife.app.Model.User.User;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.uuid.UuidGenerator;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Table(name = "chat_thread")
@Entity(name = "chat_thread")
public class ChatThread {

    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
//    @GenericGenerator(type = UuidGenerator.class, name = "thread-id")
    public UUID id;

    public String title;

    @ManyToOne
    @JoinColumn(name = "trainer_id", referencedColumnName = "id")
    public Trainer trainer;

    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "userId")
    public User user;

    @OneToMany(
        mappedBy = "thread",
        orphanRemoval = true,
        cascade = CascadeType.ALL
    )
    public List<Chat> chats;

}
