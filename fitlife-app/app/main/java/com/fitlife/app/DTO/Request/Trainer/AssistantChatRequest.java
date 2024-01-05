package com.fitlife.app.DTO.Request.Trainer;

import com.trainer.models.api.completion.chat.ChatCompletionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AssistantChatRequest {
    public String trainerId;
    public ChatCompletionRequest request;
}
