package com.fitlife.app.Model.Trainer;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.id.uuid.UuidGenerator;

import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Entity
public class Chat {
    //ID of the message in the FitLife database
    @Id
    @GenericGenerator(name = "system-uuid", type = UuidGenerator.class)
    public String id;
    //ID of the message in the open ai database
    public String message;
    public String messageId;

    @ManyToOne(optional = false,cascade = CascadeType.ALL)
    @JoinColumn(
            name = "thread_id",
            referencedColumnName = "id"
    )
    public ChatThread thread;

}
