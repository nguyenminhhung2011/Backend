package com.fitlife.app.Service.Trainer.Thread;

import com.fitlife.app.Model.Trainer.Chat;
import com.fitlife.app.Model.Trainer.ChatThread;
import com.fitlife.app.ReactiveRepository.Trainer.ChatThreadRepository;
import com.fitlife.app.Repository.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ChatThreadService {
    private final ChatThreadRepository chatThreadRepository;
    private final UserRepository userRepository;

    public Flux<ChatThread> getChatThreads(String userId) {
        return chatThreadRepository.findAllByTrainerId(userId);
    }

    public Mono<ChatThread> getChatThread(String threadId) {
        return chatThreadRepository.findById(threadId);
    }

    public Mono<Void> deleteChatThread(String id) {
        return chatThreadRepository.deleteById(id);
    }

    public Mono<List<Chat>> getChat(String threadId) {
        return chatThreadRepository
                .findById(threadId)
                .map(chatThread -> chatThread.chats);
    }

    public Mono<ChatThread> createChatThread(String title,long userId) {
        ChatThread chatThread = ChatThread
                .builder()
                .user(userRepository.findById(userId).orElseThrow())
                .title(title)
                .build();
        return chatThreadRepository.save(chatThread);
    }

    public Mono<ChatThread> addNewChat(String threadId, Chat chat) {
        return chatThreadRepository.findById(threadId)
                .map(chatThread -> {
                    chatThread.chats.add(chat);
                    return chatThread;
                })
                .flatMap(chatThreadRepository::save);
    }

    public Mono<ChatThread> updateChatThread(String threadId, ChatThread chatThread) {
        return chatThreadRepository.findById(threadId)
                .map(thread -> {
                    thread.setTitle(chatThread.title);
                    return thread;
                })
                .flatMap(chatThreadRepository::save);
    }
}
