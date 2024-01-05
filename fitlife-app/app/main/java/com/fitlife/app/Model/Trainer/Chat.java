package com.fitlife.app.Model.Trainer;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.uuid.UuidGenerator;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table
public class Chat {
    //ID of the message in the FitLife database
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
//    @GenericGenerator(type = UuidGenerator.class, name = "chat-id")
    public UUID id;

    public String message;

//    //ID of the message in the open ai database
//    public String messageId;

    @ManyToOne
    @JoinColumn(
            name = "thread_id",
            referencedColumnName = "id"
    )
    public ChatThread thread;

}
