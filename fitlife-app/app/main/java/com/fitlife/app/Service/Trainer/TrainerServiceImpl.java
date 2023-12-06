package com.fitlife.app.Service.Trainer;

import com.trainer.models.api.completion.CompletionChoice;
import com.trainer.models.api.completion.CompletionRequest;
import com.trainer.service.OpenAiService;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
public class TrainerServiceImpl {
    private final OpenAiService openAiService;

    public TrainerServiceImpl(OpenAiService aiService) {
        this.openAiService = aiService;
    }

    public String chat(String content){
       return openAiService.createCompletion(CompletionRequest
                       .builder()
                       .prompt(content)
                       .build())
               .getChoices()
               .stream()
               .map(CompletionChoice::getText)
               .collect(Collectors.joining());
    }

}
