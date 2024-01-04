package com.fitlife.app.Service.Trainer.Assistant;

import com.fitlife.app.Model.User.User;
import com.fitlife.app.Repository.User.UserRepository;
import com.trainer.models.api.completion.chat.ChatCompletionRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class AssistantServiceTest {
    User user;

    @Autowired
    AssistantService assistantService;

    @Autowired
    UserRepository userRepository;

    @BeforeEach
    void setUp() {
        user = User.builder()
                .build();
        user =  userRepository.save(user);
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
        assistantService
                .generateCompletion(user.getId(), ChatCompletionRequest.builder().build())
                .subscribe(System.out::println);
    }
}