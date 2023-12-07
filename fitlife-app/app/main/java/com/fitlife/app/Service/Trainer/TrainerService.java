package com.fitlife.app.Service.Trainer;

import com.trainer.models.api.completion.chat.ChatCompletionRequest;
import com.trainer.models.api.completion.chat.ChatMessage;
import com.trainer.service.OpenAiService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@ComponentScan("com.trainer")
@AllArgsConstructor
public class TrainerService {
    private final OpenAiService openAiService;

    public String chat(String message){
        return openAiService.createChatCompletion( ChatCompletionRequest
                .builder()
                        .messages(List.of(
                                new ChatMessage("trainer", message)
                        ))
                .build())
                .getChoices()
                .stream().map(c -> c.getMessage().content)
                .collect(Collectors.joining());
    }
}
