package com.fitlife.app.service.trainer.Chat;

import com.fitlife.app.dataClass.request.trainer.ChatDto;
import com.fitlife.app.model.trainer.Chat;
import com.fitlife.app.repository.jpa.trainer.ChatJpaRepository;
import com.fitlife.app.repository.r2dbc.trainer.ChatR2dbcRepository;
import com.fitlife.app.utils.mapper.trainer.ChatMapper;
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
    private final ChatMapper chatMapper;

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

    public Flux<ChatDto> getChatByThread(UUID threadId) {
        return chatR2dbcRepository.findAllByThread(threadId).map(chatMapper::toDto);
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
