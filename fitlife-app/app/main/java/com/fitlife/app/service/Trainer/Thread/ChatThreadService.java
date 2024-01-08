package com.fitlife.app.service.Trainer.Thread;

import com.fitlife.app.dataclass.request.trainer.ChatThreadDto;
import com.fitlife.app.dataclass.request.trainer.CreateChatThreadRequest;
import com.fitlife.app.model.Trainer.Chat;
import com.fitlife.app.model.Trainer.ChatThread;
import com.fitlife.app.ReactiveRepository.trainer.ChatThreadR2dbcRepository;
import com.fitlife.app.repository.Trainer.ChatThreadJpaRepository;
import com.fitlife.app.service.Trainer.Chat.ChatService;
import com.fitlife.app.utils.mapper.trainer.ChatThreadMapper;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ChatThreadService {
    private final ChatThreadR2dbcRepository chatThreadR2dbcRepository;
    private final ChatThreadJpaRepository chatThreadJpaRepository;
    private final ChatService chatService;
    private final ChatThreadMapper chatThreadMapper;

    public ChatThreadService(
            ChatThreadR2dbcRepository chatThreadR2dbcRepository,
            ChatThreadJpaRepository chatThreadJpaRepository,
            ChatService chatService,
            ChatThreadMapper chatThreadMapper
    ) {
        this.chatThreadR2dbcRepository = chatThreadR2dbcRepository;
        this.chatThreadJpaRepository = chatThreadJpaRepository;
        this.chatService = chatService;
        this.chatThreadMapper = chatThreadMapper;
    }

    public Mono<Long> count() {
        return chatThreadR2dbcRepository.count();
    }

    public Flux<ChatThread> getThreadByTrainerId(UUID trainerId) {
        return chatThreadR2dbcRepository.findAllByTrainer(trainerId);
    }

    public Flux<ChatThreadDto> getThreadsByUserId(long userId) {
        return chatThreadR2dbcRepository
                .findAllByUserId(userId)
                .map(chatThreadMapper::chatThreadDto);
    }


    public Mono<ChatThreadDto> getThreadById(UUID threadId) {
        return chatThreadR2dbcRepository.findById(threadId).flatMap(
                chatThread -> chatService
                        .getChatByThread(chatThread.getId())
                        .collectList()
                        .map(chats -> ChatThreadDto.builder()
                                .id(chatThread.getId())
                                .title(chatThread.getTitle())
                                .chats(chats)
                                .trainer(null)
                                .build()));
    }

    public Mono<Void> deleteThread(UUID threadId) {
        return chatThreadR2dbcRepository.deleteById(threadId);
    }

    public ChatThreadDto createChatThread(CreateChatThreadRequest request) {
        final var savedThread = chatThreadJpaRepository.save(ChatThread.builder()
                .title(request.getTitle())
                .build());

        return ChatThreadDto.builder()
                .id(savedThread.getId())
                .title(savedThread.getTitle())
                .chats(null)
                .trainer(null)
                .build();
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
