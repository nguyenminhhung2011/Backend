package com.fitlife.app.service.trainer.assistant;

import com.fitlife.app.model.trainer.Trainer;
import com.fitlife.app.model.user.User;
import com.fitlife.app.repository.jpa.user.UserRepository;
import com.fitlife.app.repository.r2dbc.trainer.ChatThreadR2dbcRepository;
import com.fitlife.app.service.trainer.thread.ChatThreadService;
import com.fitlife.app.service.trainer.trainer.TrainerService;
import com.fitlife.app.utils.mapper.trainer.TrainerMapper;
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
class AssistantChatServiceTest {
    User user;
    Trainer trainer;

    @Autowired
    AssistantChatService assistantChatService;

    @Autowired
    ChatThreadService chatThreadService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatThreadR2dbcRepository chatThreadR2dbcRepository;
    ChatCompletionRequest chatCompletionRequest;

    @Autowired
    TrainerService trainerService;

    @Autowired
    TrainerMapper trainerMapper;

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

        var dto = trainerService.createTrainer(user, trainerMapper.toDto(trainer));

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
//        final var result = assistantService
//                .generateCompletion(user,trainer, chatCompletionRequest).block();
//        System.out.println(result);
    }
}