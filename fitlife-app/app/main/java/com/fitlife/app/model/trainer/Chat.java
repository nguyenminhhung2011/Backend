package com.fitlife.app.model.trainer;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@Entity
@Table(name = "chat")
public class Chat {
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String oaiMessageId;

    @Column(length = 1024,nullable = false)
    public String message;

    @NonNull
    public String role;

    @ManyToOne
    @JoinColumn(
            name = "thread_id",
            referencedColumnName = "id"
    )
    public ChatThread thread;

}
