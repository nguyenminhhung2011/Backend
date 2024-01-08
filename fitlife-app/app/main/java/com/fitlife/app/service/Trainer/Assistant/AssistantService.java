package com.fitlife.app.Service.Trainer.Assistant;

import com.fitlife.app.DTO.Request.Trainer.AssistantChatRequest;
import com.fitlife.app.DTO.Request.Trainer.ChatDto;
import com.fitlife.app.DTO.Response.Trainer.AssistantChatResponse;
import com.fitlife.app.Model.Trainer.Chat;
import com.fitlife.app.Model.Trainer.ChatThread;
import com.fitlife.app.Model.User.User;
import com.fitlife.app.ReactiveRepository.Trainer.ChatThreadR2dbcRepository;
import com.fitlife.app.Repository.Trainer.ChatJpaRepository;
import com.fitlife.app.Repository.Trainer.ChatThreadJpaRepository;
import com.fitlife.app.Service.Trainer.Chat.ChatService;
import com.fitlife.app.Service.Trainer.Thread.ChatThreadService;
import com.fitlife.app.Utils.Mapper.trainer.ChatThreadMapper;
import com.trainer.models.api.completion.chat.ChatCompletionRequest;
import com.trainer.models.api.completion.chat.ChatMessage;
import com.trainer.models.api.completion.chat.ChatMessageRole;
import com.trainer.models.api.embedding.Embedding;
import com.trainer.models.api.embedding.EmbeddingRequest;
import com.trainer.models.api.embedding.EmbeddingResult;
import com.trainer.service.OpenAiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.time.Instant;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class AssistantService {
    private final ChatThreadService chatThreadService;
    private final ChatJpaRepository chatJpaRepository;
    private final OpenAiService openAiService;
    private final ChatThreadR2dbcRepository chatThreadR2dbcRepository;
    private final ChatThreadJpaRepository chatThreadJpaRepository;

    public Mono<List<Embedding>> createEmbedding(List<String> text) {
        return Mono.just(text)
                .map(s -> openAiService.createEmbeddings(EmbeddingRequest.builder()
                        .model("gpt-3.5-turbo")
                        .input(text)
                        .build()))
                .map(EmbeddingResult::getData);
    }

    public Flux<String> generateCompletionStream(User user, AssistantChatRequest request) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> chatThreadJpaRepository.save(ChatThread
                        .builder()
                        .title(Instant.now().toString())
                        .user(user).build())))
                .flatMapMany(chatThread -> generateCompletionStream(chatThread.getId(), request));
    }

    public Flux<String> generateCompletionStream(UUID threadId, AssistantChatRequest request) {


        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
                .builder()
                .model("gpt-3.5-turbo")
                .messages(request.getMessages().stream().map(
                        s -> ChatMessage.builder()
                                .role(ChatMessageRole.USER.value())
                                .content(s)
                                .build()
                ).toList())
                .logitBias(new HashMap<>())
                .stream(true)
                .build();

        AtomicReference<String> message = new AtomicReference<>();
        return Flux.from(openAiService.streamChatCompletion(chatCompletionRequest))
                .map(chatCompletionChunk -> chatCompletionChunk.getChoices().get(0).getMessage().getContent())
                .doOnEach(stringSignal -> {
                    if (stringSignal.hasValue()) {
                        message.set(message.get() + stringSignal.get());
                    }
                })
                .publishOn(Schedulers.boundedElastic())
                .doOnComplete(() -> {
                    if (Objects.nonNull(message.get())) {
                        chatThreadR2dbcRepository
                                .findById(threadId)
                                .subscribe(chatThread ->
                                        chatThreadService
                                                .addNewChat(threadId, Chat.builder()
                                                        .message(message.get())
                                                        .thread(chatThread)
                                                        .build()));
                    }
                });
    }

    public Mono<AssistantChatResponse> generateCompletion(User user, AssistantChatRequest request) {



        return Mono.fromFuture(
                        CompletableFuture.supplyAsync(() -> chatThreadJpaRepository.save(ChatThread
                                .builder()
                                .title(Instant.now().toString())
                                .user(user)
                                .build()))
                )
                .flatMap(chatThread -> generateCompletion(chatThread.getId(), request));

    }

    public Mono<AssistantChatResponse> generateCompletion(UUID chatThreadId, AssistantChatRequest request) {

        return chatThreadR2dbcRepository.findById(chatThreadId).flatMap(chatThread -> {
            List<ChatMessage> chats;

            if (chatThread.getChats() != null) {
                chats = new ArrayList<>(chatThread.getChats().stream().map(chat -> ChatMessage.builder()
                        .role(chat.getRole())
                        .content(chat.getMessage())
                        .build()).toList());
            } else {
                chats = new ArrayList<>();
            }

            chats.addAll(request.getMessages().stream().map(
                    s -> ChatMessage.builder()
                            .role(ChatMessageRole.USER.value())
                            .content(s)
                            .build()
            ).toList());

            final var chatCompletionRequest = ChatCompletionRequest.builder()
                    .model(request.getModel())
                    .messages(chats)
                    .build();

            return Mono
                    .fromFuture(CompletableFuture.supplyAsync(() -> {
                        chatJpaRepository.saveAll(
                                request.getMessages().stream().map(
                                        s -> Chat.builder()
                                                .thread(chatThread)
                                                .role(ChatMessageRole.USER.value())
                                                .message(s)
                                                .build()
                                ).toList()
                        );
                        return openAiService.createChatCompletion(chatCompletionRequest);
                    }))
                    .map(chatCompletionResult -> chatCompletionResult.getChoices().get(0).getMessage().getContent())

                    .publishOn(Schedulers.boundedElastic())
                    .map(
                            message -> {
                                final var chat = Chat.builder()
                                        .message(message)
                                        .thread(chatThread)
                                        .role(ChatMessageRole.SYSTEM.value())
                                        .build();

                                final var savedChat = chatThreadService.addNewChat(chatThreadId, chat);

                                return AssistantChatResponse
                                        .builder()
                                        .threadId(chatThreadId)
                                        .chat(
                                                ChatDto
                                                        .builder()
                                                        .id(savedChat.getId())
                                                        .message(savedChat.getMessage())
                                                        .role(savedChat.getRole())
                                                        .build()
                                        )
                                        .build();
                            }
                    );
        });

    }


}
