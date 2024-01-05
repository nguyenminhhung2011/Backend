package com.fitlife.app.Service.Trainer.Chat;

import com.fitlife.app.Model.Trainer.Chat;
import com.fitlife.app.ReactiveRepository.Trainer.ChatR2dbcRepository;
import com.fitlife.app.Repository.Trainer.ChatJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ChatService  {
    private final ChatR2dbcRepository chatR2dbcRepository;
    private final ChatJpaRepository chatJpaRepository;

    public Chat createChat(Chat chat) {
        return chatJpaRepository.save(chat);
    }

    public Chat updateChat(Chat chat) {
        return chatJpaRepository.save(chat);
    }

    public void deleteChat(UUID chatId) {
        chatJpaRepository.deleteById(chatId);
    }

    public Mono<Chat> getChatById(UUID id) {
        return chatR2dbcRepository.findById(id);
    }

    public Flux<Chat> getChatByThread(UUID threadId) {
        return chatR2dbcRepository.findAllByThread(threadId);
    }

    public Mono<Void> deleteById(UUID id) {
        return chatR2dbcRepository.deleteById(id);
    }

    public Chat update(Chat chat) {
        return chatJpaRepository.save(chat);
    }

    public Mono<Long> count() {
        return chatR2dbcRepository.count();
    }

}
