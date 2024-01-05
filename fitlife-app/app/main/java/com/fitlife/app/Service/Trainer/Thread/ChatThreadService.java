package com.fitlife.app.Service.Trainer.Thread;

import com.fitlife.app.Model.Trainer.Chat;
import com.fitlife.app.Model.Trainer.ChatThread;
import com.fitlife.app.ReactiveRepository.Trainer.ChatThreadRepository;
import com.fitlife.app.Repository.User.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.r2dbc.core.DatabaseClient;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.FutureTask;

@Service
@AllArgsConstructor
public class ChatThreadService {
    private final ChatThreadRepository chatThreadRepository;
    private final UserRepository userRepository;
    private DatabaseClient client;

    public Flux<ChatThread> getChatThreads(long userId) {
        return chatThreadRepository.findAllByUser(userId);
    }

    public Mono<Long> count(){
        return chatThreadRepository.count();
    }

    public Mono<ChatThread> getChatThread(UUID threadId) {
        return chatThreadRepository.findById(threadId);
    }

    public Mono<Void> deleteChatThread(UUID id) {
        return chatThreadRepository.deleteById(id);
    }

    public Mono<List<Chat>> getChat(String threadId) {
//        return chatThreadRepository
//                .findById(threadId)
//                .map(chatThread -> chatThread.chats);
        return null;
    }

    public Mono<ChatThread> createChatThread(String title, long userId) {
//       return Mono.fromFuture(
//                        CompletableFuture.supplyAsync(() -> userRepository
//                                .findById(userId)
//                                .orElseThrow()))
//                .flatMap(
//                        user -> {
//                            ChatThread chatThread = ChatThread
//                                    .builder()
//                                    .user(user)
//                                    .title(title)
//                                    .build();
//                            return chatThreadRepository.save(chatThread);
//                        });

          ChatThread chatThread = ChatThread
                .builder()
                .title(title)
                .build();
        return chatThreadRepository.save(chatThread);
    }

    public Mono<ChatThread> addNewChat(UUID threadId, Chat chat) {
//        return chatThreadRepository.findById(threadId)
//                .map(chatThread -> {
//                    chatThread.chats.add(chat);
//                    return chatThread;
//                })
//                .flatMap(chatThreadRepository::save);
        return null;
    }

    public Mono<ChatThread> updateChatThread(UUID threadId, ChatThread chatThread) {
        return chatThreadRepository.findById(threadId)
                .map(thread -> {
                    thread.setTitle(chatThread.title);
                    return thread;
                })
                .flatMap(chatThreadRepository::save);
    }
}
