package com.fitlife.app.DTO.Response.Trainer;

import com.fitlife.app.DTO.Request.Trainer.ChatDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class AssistantChatResponse {
    public final UUID threadId;
    public final ChatDto chat;
}
