package com.fitlife.app.DTO.Request.Trainer;

import com.trainer.models.api.completion.chat.ChatCompletionRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AssistantChatRequest {
    public String model = "gpt-3.5-turbo";
    public List<String> messages;
}
