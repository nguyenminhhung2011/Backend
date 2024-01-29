package com.fitlife.app.dataClass.response.trainer;

import com.fitlife.app.dataClass.dto.trainer.ChatDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@AllArgsConstructor
@Builder
public class TrainerChatResponse {
    public final UUID threadId;
    public final ChatDto chat;
}
