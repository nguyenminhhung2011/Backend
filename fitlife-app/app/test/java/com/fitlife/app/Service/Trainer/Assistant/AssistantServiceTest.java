package com.fitlife.app.Service.Trainer.Assistant;

import com.fitlife.app.Model.Trainer.ChatThread;
import com.fitlife.app.Model.User.User;
import com.fitlife.app.ReactiveRepository.Trainer.ChatThreadRepository;
import com.fitlife.app.Repository.User.UserRepository;
import com.fitlife.app.Service.Trainer.Thread.ChatThreadService;
import com.trainer.models.api.completion.chat.ChatCompletionRequest;
import com.trainer.models.api.completion.chat.ChatMessage;
import com.trainer.models.api.completion.chat.ChatMessageRole;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AssistantServiceTest {
    User user;

    @Autowired
    AssistantService assistantService;

    @Autowired
    ChatThreadService chatThreadService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    ChatThreadRepository chatThreadRepository;
    ChatCompletionRequest chatCompletionRequest;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .username("hoang")
                .password("123456")
                .build();
        user = userRepository.save(user);

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
//        assistantService
//                .generateCompletion(user.getId(), chatCompletionRequest)
//                .subscribe(System.out::println);
        chatThreadService.createChatThread("Testing", user.getId()).subscribe(System.out::println);

     chatThreadService.count().subscribe(System.out::println);
    }
}