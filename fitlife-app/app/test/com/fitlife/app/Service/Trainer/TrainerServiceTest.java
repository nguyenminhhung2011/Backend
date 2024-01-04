package com.fitlife.app.Service.Trainer;

import com.fitlife.app.FitnessAppApplication;
import com.trainer.models.api.completion.CompletionChoice;
import com.trainer.models.api.completion.CompletionRequest;
import com.trainer.models.api.completion.chat.ChatCompletionRequest;
import com.trainer.models.api.completion.chat.ChatMessage;
import com.trainer.models.api.completion.chat.ChatMessageRole;
import com.trainer.service.OpenAiService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScans;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;


@SpringBootTest(classes = {OpenAiService.class, FitnessAppApplication.class})
class TrainerServiceTest {

    @Autowired
    TrainerService trainerService;

    @Autowired
    OpenAiService service;

    @Test
    void createChatStream() {
        trainerService.createChatStream( "Hello").toStream().forEach(chatMessage -> {
            System.out.println(chatMessage.message);
        });
    }

    @Test
    void streamChatCompletion() {

        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "Hello");
        messages.add(systemMessage);

        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .logitBias(new HashMap<>())
                .stream(true)
                .build();

        service.streamChatCompletion(chatCompletionRequest).blockingForEach(chatCompletionChunk -> {
            chatCompletionChunk.getChoices().forEach(chatCompletionChoice -> {
                System.out.println(chatCompletionChoice.getMessage().getContent());
            });
        });
    }
    @Test
    void createCompletion() {
        CompletionRequest completionRequest = CompletionRequest.builder()
                .model("ada")
                .prompt("Somebody once told me the world is gonna roll me")
                .echo(true)
                .n(5)
                .maxTokens(50)
                .user("testing")
                .logitBias(new HashMap<>())
                .logprobs(5)
                .build();

        List<CompletionChoice> choices = service.createCompletion(completionRequest).getChoices();
        assertEquals(5, choices.size());
        choices.forEach(completionChoice -> System.out.println("//////" + completionChoice.getText() + "/////"));
        assertNotNull(choices.get(0).getLogprobs());
    }
}