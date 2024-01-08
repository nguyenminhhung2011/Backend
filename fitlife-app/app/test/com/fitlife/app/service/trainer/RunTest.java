package com.fitlife.app.service.trainer;

import com.trainer.models.OpenAiResponse;
import com.trainer.models.api.assistants.Assistant;
import com.trainer.models.api.assistants.AssistantRequest;
import com.trainer.models.api.message.Message;
import com.trainer.models.api.message.MessageRequest;
import com.trainer.models.api.runs.Run;
import com.trainer.models.api.runs.RunCreateRequest;
import com.trainer.models.api.threads.Thread;
import com.trainer.models.api.threads.ThreadRequest;
import com.trainer.service.OpenAiService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(classes = OpenAiService.class)
class RunTest {
//    String token = "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";
    OpenAiService service;

    @Test
    @Timeout(30)
    void createRetrieveRun() {
        AssistantRequest assistantRequest = AssistantRequest.builder()
                .model("")
                .name("MATH_TUTOR")
                .instructions("You are a personal Math Tutor.")
                .build();
        Assistant assistant = service.createAssistant(assistantRequest);

        ThreadRequest threadRequest = ThreadRequest.builder()
                .build();
        Thread thread = service.createThread(threadRequest);

        MessageRequest messageRequest = MessageRequest.builder()
                .content("Hello")
                .build();

        Message message = service.createMessage(thread.getId(), messageRequest);

        RunCreateRequest runCreateRequest = RunCreateRequest.builder()
                .assistantId(assistant.getId())
                .build();

        Run run = service.createRun(thread.getId(), runCreateRequest);
        assertNotNull(run);

        Run retrievedRun;
        do {
            retrievedRun = service.retrieveRun(thread.getId(), run.getId());
            assertEquals(run.getId(), retrievedRun.getId());
        }
        while (!(retrievedRun.getStatus().equals("completed")) && !(retrievedRun.getStatus().equals("failed")));


        assertNotNull(retrievedRun);

        OpenAiResponse<Message> response = service.listMessages(thread.getId());

        List<Message> messages = response.getData();
        assertEquals(2, messages.size());
        assertEquals("user", messages.get(1).role());
        assertEquals("assistant", messages.get(0).role());
    }
}
