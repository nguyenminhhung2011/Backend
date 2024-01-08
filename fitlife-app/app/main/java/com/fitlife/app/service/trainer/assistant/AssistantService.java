package com.fitlife.app.service.trainer.assistant;

import com.fitlife.app.repository.jpa.trainer.ChatJpaRepository;
import com.fitlife.app.repository.jpa.trainer.ChatThreadJpaRepository;
import com.fitlife.app.repository.r2dbc.trainer.ChatR2dbcRepository;
import com.fitlife.app.dataClass.request.trainer.AssistantChatRequest;
import com.fitlife.app.dataClass.dto.trainer.ChatDto;
import com.fitlife.app.dataClass.response.trainer.AssistantChatResponse;
import com.fitlife.app.dataClass.response.trainer.AssistantChatStreamResponse;
import com.fitlife.app.model.trainer.Chat;
import com.fitlife.app.model.trainer.ChatThread;
import com.fitlife.app.model.user.User;
import com.fitlife.app.repository.r2dbc.trainer.ChatThreadR2dbcRepository;
import com.fitlife.app.service.trainer.thread.ChatThreadService;
import com.fitlife.app.utils.mapper.trainer.ChatMapper;
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
    private final OpenAiService openAiService;
    private final ChatJpaRepository chatJpaRepository;
    private final ChatR2dbcRepository chatR2dbcRepository;
    private final ChatThreadR2dbcRepository chatThreadR2dbcRepository;
    private final ChatThreadJpaRepository chatThreadJpaRepository;
    private ChatMapper chatMapper;

    public Mono<List<Embedding>> createEmbedding(List<String> text) {
        return Mono.just(text)
                .map(s -> openAiService.createEmbeddings(EmbeddingRequest.builder()
                        .model("gpt-3.5-turbo")
                        .input(text)
                        .build()))
                .map(EmbeddingResult::getData);
    }

    public Flux<AssistantChatStreamResponse> generateCompletionStream(User user, AssistantChatRequest request) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> chatThreadJpaRepository.save(ChatThread
                        .builder()
                        .title(Instant.now().toString())
                        .user(user).build())))
                .flatMapMany(chatThread -> generateCompletionStream(chatThread.getId(), request));
    }

    public Flux<AssistantChatStreamResponse> generateCompletionStream(UUID threadId, AssistantChatRequest request) {

        return chatR2dbcRepository
                .findAllByThread(threadId)
                .collectList()
                .flatMapMany(oldChat -> {
                    var chatCompletionRequest = _buildChatCompletionRequest(oldChat, request)
                            .logitBias(new HashMap<>())
                            .stream(true).build();

//        final List<ChatMessage> messages = new ArrayList<>();
//        final ChatMessage systemMessage = new ChatMessage(ChatMessageRole.SYSTEM.value(), "Hello");
//        messages.add(systemMessage);
//        ChatCompletionRequest chatCompletionRequest = ChatCompletionRequest
//                .builder()
//                .model("gpt-3.5-turbo")
//                .messages(messages)
//                .logitBias(new HashMap<>())
//                .stream(true)
//                .build();

//                    return chatThreadR2dbcRepository
//                            .findById(threadId)
//                            .flatMapMany(chatThread ->
//                                    Mono.fromFuture(CompletableFuture.supplyAsync(() ->
//                                                    chatJpaRepository.saveAll(
//                                                            request.getMessages().stream().map(
//                                                                    s -> Chat.builder()
//                                                                            .thread(chatThread)
//                                                                            .role(ChatMessageRole.USER.value())
//                                                                            .message(s)
//                                                                            .build()
//                                                            ).toList()
//                                                    )))
//                                            .transform(savedChat -> {
                    AtomicReference<String> message = new AtomicReference<>("");

                    return Flux.from(openAiService.streamChatCompletion(chatCompletionRequest))
                            .map(chatCompletionChunk -> {
                                String value = Objects.requireNonNullElse(chatCompletionChunk.getChoices().get(0).getMessage().getContent(), "");
                                return AssistantChatStreamResponse.builder()
                                        .threadId(threadId)
                                        .message(value)
                                        .build();
                            })
                            .doOnEach(stringSignal -> {
                                if (stringSignal.hasValue()) {
                                    message.set(message.get() + Objects.requireNonNull(stringSignal.get()).getMessage());
                                }
                            })
                            .publishOn(Schedulers.boundedElastic())
                            .doOnComplete(() -> {
                                if (Objects.nonNull(message.get())) {
                                    request.getMessages().stream().map(
                                            s -> Chat.builder()
                                                    .role(ChatMessageRole.USER.value())
                                                    .message(s)
                                                    .build()
                                    ).forEach(chat -> chatThreadService
                                            .addNewChat(threadId, chat));
                                    chatThreadService
                                            .addNewChat(threadId, Chat.builder()
                                                    .message(message.get())
                                                    .role(ChatMessageRole.SYSTEM.value())
                                                    .build());
                                }
                            });
//                                            }));
                });
    }

    private ChatCompletionRequest.ChatCompletionRequestBuilder _buildChatCompletionRequest(List<Chat> oldChat, AssistantChatRequest request) {
        List<ChatMessage> chats = new ArrayList<>(oldChat.stream().map(chat -> ChatMessage.builder()
                .role(chat.getRole())
                .content(chat.getMessage())
                .build()).toList());

        chats.addAll(request.getMessages().stream().map(
                s -> ChatMessage.builder()
                        .role(ChatMessageRole.USER.value())
                        .content(s)
                        .build()
        ).toList());

        return ChatCompletionRequest.builder()
                .model(request.getModel())
                .messages(chats);
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

        return chatR2dbcRepository.findAllByThread(chatThreadId).collectList().flatMap(oldChat -> {

            final var chatCompletionRequest = _buildChatCompletionRequest(oldChat, request).build();

            return chatThreadR2dbcRepository.findById(chatThreadId).flatMap(chatThread -> Mono
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
                    ));
        });

    }


}
