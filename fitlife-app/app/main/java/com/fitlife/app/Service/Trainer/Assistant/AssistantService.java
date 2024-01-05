package com.fitlife.app.Service.Trainer.Assistant;

import com.fitlife.app.Model.Trainer.Chat;
import com.fitlife.app.Model.Trainer.ChatThread;
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

import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@AllArgsConstructor
public class AssistantService {
    private final ChatThreadService chatThreadService;
    private final ChatService chatService;
    private final OpenAiService openAiService;

    public Mono<List<Embedding>> createEmbedding(List<String> text) {
        return Mono.just(text)
                .map(s -> openAiService.createEmbeddings(EmbeddingRequest.builder()
                        .model("gpt-3.5-turbo")
                        .input(text)
                        .build()))
                .map(EmbeddingResult::getData);
    }

    public Flux<String> generateCompletionStream(long userId, ChatCompletionRequest chatCompletionRequest) {
        return chatThreadService.createChatThread("Chat with assistant at " + Instant.now(), userId)
                .map(ChatThread::getId)
                .flatMapMany(threadId -> generateCompletionStream(threadId, chatCompletionRequest));
    }

    public Flux<String> generateCompletionStream(UUID threadId, ChatCompletionRequest chatCompletionRequest) {
        return Flux.from(openAiService.streamChatCompletion(chatCompletionRequest))
                .map(chatCompletionChunk -> chatCompletionChunk.getChoices().get(0).getMessage().getContent())
                .doOnComplete(() -> chatThreadService.addNewChat(threadId, Chat.builder()
                        .message("Chat completed")
                        .build())
                );
    }

    public Mono<String> generateCompletion(long userId, ChatCompletionRequest chatCompletionRequest) {
        return chatThreadService.createChatThread("Chat with assistant at " + Instant.now(), userId)
                .map(ChatThread::getId)
                .flatMap(threadId -> generateCompletion(threadId, chatCompletionRequest));
    }

    public Mono<String> generateCompletion(UUID chatThreadId, ChatCompletionRequest chatCompletionRequest) {
        return Mono
                .just(openAiService.createChatCompletion(chatCompletionRequest))
                .map(chatCompletionResult -> {
                    String message = chatCompletionResult.getChoices().get(0).getMessage().getContent();
                    System.out.println(message);
                    return message;
                })
                .publishOn(Schedulers.boundedElastic())
                .doFinally(
                        message -> chatThreadService.getChatThread(chatThreadId)
                                .map(chatThread -> Chat.builder()
                                        .thread(chatThread)
                                        .build())
                                .transform(chatService::create)
                                .subscribe(chat -> chatThreadService.addNewChat(chatThreadId, chat))
                );
    }


}
