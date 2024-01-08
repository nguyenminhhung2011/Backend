package com.fitlife.app.service.trainer.thread;

import com.fitlife.app.controller.trainer.AssistantController;
import com.fitlife.app.dataClass.dto.trainer.ChatThreadDetailDto;
import com.fitlife.app.dataClass.request.trainer.CreateChatThreadRequest;
import com.fitlife.app.model.trainer.Chat;
import com.fitlife.app.model.trainer.ChatThread;
import com.fitlife.app.repository.jpa.trainer.ChatThreadJpaRepository;
import com.fitlife.app.repository.r2dbc.trainer.ChatThreadR2dbcRepository;
import com.fitlife.app.service.trainer.chat.ChatService;
import com.fitlife.app.utils.mapper.trainer.ChatThreadMapper;
import com.trainer.models.api.threads.Thread;
import com.trainer.models.api.threads.ThreadRequest;
import com.trainer.service.OpenAiService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ChatThreadService {
    private final ChatThreadR2dbcRepository chatThreadR2dbcRepository;
    private final ChatThreadJpaRepository chatThreadJpaRepository;
    private final ChatService chatService;
    private final ChatThreadMapper chatThreadMapper;
    private final OpenAiService openAiService;

    public ChatThreadService(
            ChatThreadR2dbcRepository chatThreadR2dbcRepository,
            ChatThreadJpaRepository chatThreadJpaRepository,
            ChatService chatService,
            ChatThreadMapper chatThreadMapper, OpenAiService openAiService
    ) {
        this.chatThreadR2dbcRepository = chatThreadR2dbcRepository;
        this.chatThreadJpaRepository = chatThreadJpaRepository;
        this.chatService = chatService;
        this.chatThreadMapper = chatThreadMapper;
        this.openAiService = openAiService;
    }

    public Mono<Long> count() {
        return chatThreadR2dbcRepository.count();
    }

    public Flux<ChatThread> getThreadByTrainerId(UUID trainerId) {
        return chatThreadR2dbcRepository.findAllByTrainer(trainerId);
    }

    public Flux<ChatThreadDetailDto> getThreadsByUserId(long userId) {
        return chatThreadR2dbcRepository
                .findAllByUserId(userId)
                .map(chatThreadMapper::toDetailDto);
    }


    public Mono<ChatThreadDetailDto> getThreadById(UUID threadId) {
        return chatThreadR2dbcRepository.findById(threadId).flatMap(
                chatThread -> chatService
                        .getChatByThread(chatThread.getId())
                        .collectList()
                        .map(chats -> ChatThreadDetailDto.builder()
                                .id(chatThread.getId())
                                .title(chatThread.getTitle())
                                .chats(chats)
                                .trainer(null)
                                .build()));
    }

    public Mono<Void> deleteThread(UUID threadId) {
        return chatThreadR2dbcRepository.deleteById(threadId);
    }

    public ChatThreadDetailDto createChatThread(CreateChatThreadRequest request) {
        final var savedThread = chatThreadJpaRepository.save(ChatThread.builder()
                .title(request.getTitle())
                .build());


        return ChatThreadDetailDto.builder()
                .id(savedThread.getId())
                .title(savedThread.getTitle())
                .chats(null)
                .trainer(null)
                .build();
    }

    public ChatThread createAssistantThread(CreateChatThreadRequest request) {

        ThreadRequest threadRequest = ThreadRequest.builder().build();
        Thread thread = openAiService.createThread(threadRequest);

        return chatThreadJpaRepository
                .save(ChatThread
                        .builder()
                        .openAiThreadId(thread.getId())
                        .title(request.getTitle())
                        .build());
    }

    public ChatThread updateChatThread(ChatThread chatThread) {
        return chatThreadJpaRepository.save(chatThread);
    }

    public Chat addNewChat(UUID threadId, Chat chat) {
        return chatThreadJpaRepository.findById(threadId)
                .map(chatThread -> {
                    chat.setThread(chatThread);
                    return chatService.createChat(chat);
                }).orElseThrow(() -> new RuntimeException("Thread not found"));
    }


    public void deleteChatThread(UUID threadId) {
        chatThreadJpaRepository.deleteById(threadId);
    }

}
