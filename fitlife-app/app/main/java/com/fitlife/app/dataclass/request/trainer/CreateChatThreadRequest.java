package com.fitlife.app.DTO.Request.Trainer;

import com.fitlife.app.Model.Trainer.Chat;
import com.fitlife.app.Model.Trainer.Trainer;
import com.fitlife.app.Model.User.User;
import jakarta.persistence.CascadeType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateChatThreadRequest {
    public UUID id;
    public String title;
}
