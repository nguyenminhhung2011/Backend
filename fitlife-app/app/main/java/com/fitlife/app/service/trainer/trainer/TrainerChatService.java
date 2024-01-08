package com.fitlife.app.service.trainer.trainer;

import com.fitlife.app.dataClass.request.trainer.CreateChatThreadRequest;
import com.fitlife.app.dataClass.response.trainer.TrainerChatResponse;
import com.fitlife.app.model.trainer.Chat;
import com.fitlife.app.model.trainer.ChatThread;
import com.fitlife.app.model.trainer.Trainer;
import com.fitlife.app.repository.jpa.trainer.ChatJpaRepository;
import com.fitlife.app.repository.jpa.trainer.ChatThreadJpaRepository;
import com.fitlife.app.repository.jpa.user.UserRepository;
import com.fitlife.app.repository.r2dbc.trainer.ChatR2dbcRepository;
import com.fitlife.app.repository.r2dbc.trainer.ChatThreadR2dbcRepository;
import com.fitlife.app.repository.r2dbc.trainer.TrainerR2dbcRepository;
import com.fitlife.app.service.trainer.chat.ChatService;
import com.fitlife.app.service.trainer.thread.ChatThreadService;
import com.fitlife.app.utils.mapper.trainer.ChatMapper;
import com.trainer.models.OpenAiResponse;
import com.trainer.models.api.completion.chat.ChatMessageRole;
import com.trainer.models.api.message.Message;
import com.trainer.models.api.runs.Run;
import com.trainer.models.api.runs.RunCreateRequest;
import com.trainer.service.OpenAiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Service
@AllArgsConstructor
public class TrainerChatService {
    private final ChatThreadService chatThreadService;
    private final OpenAiService openAiService;
    private final ChatJpaRepository chatJpaRepository;
    private final ChatR2dbcRepository chatR2dbcRepository;
    private final ChatThreadR2dbcRepository chatThreadR2dbcRepository;
    private final ChatThreadJpaRepository chatThreadJpaRepository;
    private final TrainerR2dbcRepository trainerR2dbcRepository;
    private final ChatService chatService;
    private final ChatMapper chatMapper;
    private final UserRepository userRepository;

    public Mono<TrainerChatResponse> createChat(String trainerId, long userId, String message) {

        return trainerR2dbcRepository
                .findByIdAndUser(trainerId,userId)
                .doOnError(throwable -> {
                    throw new RuntimeException("Error: " + throwable.getMessage(),throwable);
                })
                .flatMap(trainer -> Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
                    ChatThread chatThread = chatThreadService
                            .createAssistantThread(
                                    CreateChatThreadRequest
                                            .builder()
                                            .title(Instant.now().toString())
                                            .build());

                    Chat chat = chatService
                            .createAssistantChat(
                                    chatThread.getOpenAiThreadId(),
                                    Chat.builder()
                                            .role(ChatMessageRole.USER.value())
                                            .message(message)
                                            .thread(chatThread)
                                            .build());

                    RunCreateRequest runCreateRequest = RunCreateRequest.builder()
                            .assistantId(trainer.getId())
                            .build();

                    Run run = openAiService.createRun(chatThread.getOpenAiThreadId(), runCreateRequest);
                    Run retrievedRun;

                    do {
                        retrievedRun = openAiService.retrieveRun(chatThread.getOpenAiThreadId(), run.getId());
                    }
                    while (!(retrievedRun.getStatus().equals("completed")) && !(retrievedRun.getStatus().equals("failed")));

                    return Mono.fromFuture(CompletableFuture.supplyAsync(() -> {
                        return openAiService.listMessages(chatThread.getOpenAiThreadId());
                    }))
                            .map(messageOpenAiResponse -> messageOpenAiResponse.getData())
                            .flatMap(messages -> {
                                return Mono.just(TrainerChatResponse.builder().build());
                            });
                })));

    }
}

0