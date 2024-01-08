package com.fitlife.app.model.trainer;

import com.fitlife.app.model.user.User;
import jakarta.persistence.*;
import lombok.*;

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


    public List<Chat> getChats() {
        return chats;
    }
}
