package com.fitlife.app.Service.Trainer.Assistant;

import com.fitlife.app.Model.Trainer.Chat;
import com.fitlife.app.Model.Trainer.ChatThread;
import com.fitlife.app.Model.Trainer.Trainer;
import com.fitlife.app.Model.User.User;
import com.fitlife.app.Service.Trainer.Chat.ChatService;
import com.fitlife.app.Service.Trainer.Thread.ChatThreadService;
import com.trainer.models.api.completion.chat.ChatCompletionRequest;
import com.trainer.models.api.embedding.Embedding;
import com.trainer.models.api.embedding.EmbeddingRequest;
import com.trainer.models.api.embedding.EmbeddingResult;
import com.trainer.service.OpenAiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

@Service
@AllArgsConstructor
public class AssistantService {
    private final ChatThreadService chatThreadService;
    private final OpenAiService openAiService;

    public Mono<List<Embedding>> createEmbedding(List<String> text) {
        return Mono.just(text)
                .map(s -> openAiService.createEmbeddings(EmbeddingRequest.builder()
                        .model("gpt-3.5-turbo")
                        .input(text)
                        .build()))
                .map(EmbeddingResult::getData);
    }

    public Flux<String> generateCompletionStream(User user, ChatCompletionRequest chatCompletionRequest) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> chatThreadService.createChatThread(ChatThread.builder().user(user).build())))
                .flatMapMany(chatThread -> generateCompletionStream(chatThread.getId(), chatCompletionRequest));
    }

    public Flux<String> generateCompletionStream(UUID threadId, ChatCompletionRequest chatCompletionRequest) {
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
                        chatThreadService
                                .getById(threadId)
                                .subscribe(chatThread ->
                                        chatThreadService.addNewChat(threadId, Chat.builder()
                                                .message(message.get())
                                                .thread(chatThread)
                                                .build()));
                    }
                });
    }

    public Mono<String> generateCompletion(User user, ChatCompletionRequest chatCompletionRequest) {
        return Mono.fromFuture(CompletableFuture.supplyAsync(() -> chatThreadService.createChatThread(ChatThread.builder()
                        .trainer(trainer)
                        .user(user).build())))
                .flatMap(chatThread -> generateCompletion(chatThread.getId(), chatCompletionRequest))
                .doOnError(throwable -> System.out.println(throwable.getMessage()));

    }

    public Mono<String> generateCompletion(UUID chatThreadId, ChatCompletionRequest chatCompletionRequest) {
        return Mono
                .fromFuture(CompletableFuture.supplyAsync(() -> openAiService.createChatCompletion(chatCompletionRequest)))
                .map(chatCompletionResult -> chatCompletionResult.getChoices().get(0).getMessage().getContent())
                .publishOn(Schedulers.boundedElastic())
                .doOnSuccess(
                        message -> chatThreadService.addNewChat(chatThreadId, Chat.builder()
                                .message(message)
                                .thread(ChatThread.builder().id(chatThreadId).build())
                                .build())
                );

    }


}
