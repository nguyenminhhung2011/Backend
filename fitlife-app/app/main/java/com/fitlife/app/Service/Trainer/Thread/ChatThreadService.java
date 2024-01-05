package com.fitlife.app.Service.Trainer.Thread;

import com.fitlife.app.Model.Trainer.Chat;
import com.fitlife.app.Model.Trainer.ChatThread;
import com.fitlife.app.ReactiveRepository.Trainer.ChatThreadR2dbcRepository;
import com.fitlife.app.Repository.Trainer.ChatThreadJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
public class ChatThreadService {
    private final ChatThreadR2dbcRepository chatThreadR2dbcRepository;
    private final ChatThreadJpaRepository chatThreadJpaRepository;

    public ChatThreadService(ChatThreadR2dbcRepository chatThreadR2dbcRepository, ChatThreadJpaRepository chatThreadJpaRepository) {
        this.chatThreadR2dbcRepository = chatThreadR2dbcRepository;
        this.chatThreadJpaRepository = chatThreadJpaRepository;
    }

    public Mono<Long> count() {
        return chatThreadR2dbcRepository.count();
    }

    public Flux<ChatThread> getThreadByTrainerId(UUID trainerId) {
        return chatThreadR2dbcRepository.findAllByTrainer(trainerId);
    }
    public Flux<ChatThread> getThreadsByUserId(long userId) {
        return chatThreadR2dbcRepository.findAllByUser(userId);
    }

    public Mono<ChatThread> getById(UUID threadId) {
        return chatThreadR2dbcRepository.findById(threadId);
    }

    public Mono<Void> deleteThread(UUID threadId)  {
        return chatThreadR2dbcRepository.deleteById(threadId);
    }

    public Flux<Chat> getChatByThreadId(UUID threadId) {
        return chatThreadR2dbcRepository.findById(threadId)
                .flatMapIterable(ChatThread::getChats);
    }

    public ChatThread createChatThread(ChatThread chatThread) {
        return chatThreadJpaRepository.save(chatThread);
    }

    public ChatThread updateChatThread(ChatThread chatThread) {
        return chatThreadJpaRepository.save(chatThread);
    }

    public ChatThread addNewChat(UUID threadId, Chat chat) {
        return chatThreadJpaRepository.findById(threadId)
                .map(chatThread -> {
                    chat.setThread(chatThread);
                    return chatThreadJpaRepository.save(chatThread);
                }).orElseThrow(() -> new RuntimeException("Thread not found"));
    }

    public void deleteChatThread(UUID threadId) {
        chatThreadJpaRepository.deleteById(threadId);
    }

}
