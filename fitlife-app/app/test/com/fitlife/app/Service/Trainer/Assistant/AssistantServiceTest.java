package com.fitlife.app.Service.Trainer.Assistant;

import com.fitlife.app.Model.Trainer.Trainer;
import com.fitlife.app.Model.User.User;
import com.fitlife.app.ReactiveRepository.Trainer.ChatThreadR2dbcRepository;
import com.fitlife.app.Repository.User.UserRepository;
import com.fitlife.app.Service.Trainer.Thread.ChatThreadService;
import com.fitlife.app.Service.Trainer.TrainerService;
import com.trainer.models.api.completion.chat.ChatCompletionRequest;
import com.trainer.models.api.completion.chat.ChatMessage;
import com.trainer.models.api.completion.chat.ChatMessageRole;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class AssistantServiceTest {
    User user;
    Trainer trainer;

    @Autowired
    AssistantService assistantService;

    @Autowired
    ChatThreadService chatThreadService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatThreadR2dbcRepository chatThreadR2dbcRepository;
    ChatCompletionRequest chatCompletionRequest;

    @Autowired
    TrainerService trainerService;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("hoang")
                .password("123456")
                .build();
        user = userRepository.saveAndFlush(user);

        trainer = Trainer.builder()
                .name("chatgpt")
                .user(user)
                .model("gpt-3.5-turbo")
                .build();
        trainer = trainerService.createTrainer(trainer);

        final List<ChatMessage> messages = new ArrayList<>();
        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "Hello");
        messages.add(systemMessage);

        chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(messages)
                .logitBias(new HashMap<>())
                .stream(true)
                .build();
    }

    @Test
    void createEmbedding() {
    }

    @Test
    void generateCompletionStream() {
    }

    @Test
    void testGenerateCompletionStream() {
    }

    @Test
    void generateCompletion() {
        final var result = assistantService
                .generateCompletion(user,trainer, chatCompletionRequest).block();
        System.out.println(result);
    }
}