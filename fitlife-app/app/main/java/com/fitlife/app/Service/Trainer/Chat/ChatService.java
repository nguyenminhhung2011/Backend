package com.fitlife.app.Service.Trainer.Chat;

import com.fitlife.app.Model.Trainer.Chat;
import com.fitlife.app.ReactiveRepository.Trainer.ChatRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class ChatService  {
    private final ChatRepository chatRepository;

    public Mono<Chat> create(Mono<Chat> chat) {
        return chat.flatMap(chatRepository::save);
    }

    public Mono<Chat> findById(String id) {
        return chatRepository.findById(id);
    }

    public Mono<Void> deleteById(String id) {
        return chatRepository.deleteById(id);
    }

    public Mono<Chat> update(Mono<Chat> chat) {
        return chat.flatMap(chatRepository::save);
    }

    public Mono<Void> deleteAll() {
        return chatRepository.deleteAll();
    }

    public Mono<Long> count() {
        return chatRepository.count();
    }

    public Flux<Chat> getListMessage(String threadId) {
        return chatRepository.findAllByThread(threadId);
    }

}
