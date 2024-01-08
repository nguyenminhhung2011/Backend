package com.fitlife.app.dataclass.response.trainer;

import com.fitlife.app.dataclass.request.trainer.ChatDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssistantChatStreamResponse {
    public UUID threadId;
    public String message;
}
