package com.fitlife.app.dataclass.response.trainer;

import com.fitlife.app.dataclass.request.trainer.ChatDto;
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
