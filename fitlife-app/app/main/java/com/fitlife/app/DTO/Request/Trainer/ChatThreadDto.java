package com.fitlife.app.DTO.Request.Trainer;

import com.fitlife.app.DTO.DataClass.User.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatThreadDto {
    public UUID id;
    public String title;
    public TrainerDto trainer;
    public List<ChatDto> chats;
}
