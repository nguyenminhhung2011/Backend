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
    @Id
    @org.springframework.data.annotation.Id
    @GeneratedValue(strategy = GenerationType.UUID)
    public UUID id;

    public String message;

    public String role;

    @ManyToOne
    @JoinColumn(
            name = "thread_id",
            referencedColumnName = "id"
    )
    public ChatThread thread;

}
