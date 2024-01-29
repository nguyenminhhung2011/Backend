package com.fitlife.app.service.trainer.trainer;

import com.fitlife.app.dataClass.dto.trainer.ChatDto;
import com.fitlife.app.dataClass.request.trainer.CreateChatThreadRequest;
import com.fitlife.app.dataClass.request.trainer.TrainerChatRequest;
import com.fitlife.app.dataClass.response.trainer.TrainerChatResponse;
import com.fitlife.app.model.trainer.Chat;
import com.fitlife.app.model.trainer.ChatThread;
import com.fitlife.app.model.trainer.Trainer;
import com.fitlife.app.model.user.User;
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
import com.trainer.models.api.message.MessageContent;
import com.trainer.models.api.runs.Run;
import com.trainer.models.api.runs.RunCreateRequest;
import com.trainer.service.OpenAiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TrainerChatService {
    private final ChatThreadService chatThreadService;
    private final OpenAiService openAiService;
    private final ChatThreadR2dbcRepository chatThreadR2dbcRepository;
    private final TrainerR2dbcRepository trainerR2dbcRepository;
    private final ChatService chatService;
    private final ChatMapper chatMapper;

    public Mono<TrainerChatResponse> createChat(User user, TrainerChatRequest request) {
        return trainerR2dbcRepository
                .findByIdAndUser(request.getTrainerId(), user.getId())
                .doOnError(throwable -> {
                    throw new RuntimeException("Error: " + throwable.getMessage(), throwable);
                })
                .flatMap(trainer -> Mono.fromFuture(CompletableFuture.supplyAsync(() -> chatThreadService
                        .createAssistantThread(user,
                                CreateChatThreadRequest
                                        .builder()
                                        .title(Instant.now().toString())
                                        .build()))))
                .flatMap(chatThread -> createChat(user, chatThread.getId(), request));
    }

    public Mono<TrainerChatResponse> createChat(User user, UUID threadId, TrainerChatRequest request) {
        return trainerR2dbcRepository
                .findByIdAndUser(request.getTrainerId(), user.getId())
                .doOnError(throwable -> {
                    throw new RuntimeException("Error: " + throwable.getMessage(), throwable);
                })
                //Add to server
                .flatMap(trainer ->
                        chatThreadR2dbcRepository
                                .findById(threadId)
                                .doOnSuccess(chatThread -> {
                                    chatService.createAssistantChat(
                                            chatThread.getOpenAiThreadId(),
                                            Chat.builder()
                                                    .role(ChatMessageRole.USER.value())
                                                    .message(request.getMessage())
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
                                })
                                .timeout(Duration.ofSeconds(30), Mono.empty())
                                .flatMap(chatThread -> Mono
                                        .fromFuture(CompletableFuture.supplyAsync(() -> openAiService.listMessages(chatThread.getOpenAiThreadId())))
                                        .map(OpenAiResponse::getData)
                                        .map(messages -> TrainerChatResponse.builder()
                                                .threadId(chatThread.getId())
                                                .chat(chatMapper.toDto(chatService.createChat(
                                                        Chat.builder()
                                                                .oaiMessageId(messages.get(0).id())
                                                                .role(ChatMessageRole.ASSISTANT.value())
                                                                .message(messages.get(0).content().stream().map(MessageContent::text).toList().get(0).value())
                                                                .thread(chatThread)
                                                                .build())))
                                                .build())
                                )
                );
    }
}
