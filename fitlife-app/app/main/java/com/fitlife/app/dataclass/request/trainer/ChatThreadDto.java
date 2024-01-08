package com.fitlife.app.dataclass.request.trainer;

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
