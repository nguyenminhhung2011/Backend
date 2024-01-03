package com.fitlife.app.Service.Trainer;

import com.fitlife.app.DTO.Request.Trainer.SearchTrainerRequest;
import com.fitlife.app.Model.Trainer.Chat;
import com.fitlife.app.Model.Trainer.ChatThread;
import com.fitlife.app.Model.Trainer.Trainer;
import com.fitlife.app.Repository.Trainer.ChatRepository;
import com.fitlife.app.Repository.Trainer.ChatThreadRepository;
import com.fitlife.app.Repository.Trainer.TrainerRepository;
import com.trainer.models.api.completion.CompletionChoice;
import com.trainer.models.api.completion.CompletionRequest;
import com.trainer.models.api.completion.CompletionResult;
import com.trainer.models.api.threads.Thread;
import com.trainer.models.api.threads.ThreadRequest;
import com.trainer.service.OpenAiService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
@AllArgsConstructor
public class TrainerService {

    private final TrainerRepository trainerRepository;
    private final ChatThreadRepository chatThreadRepository;
    private final ChatRepository chatRepository;
    private final OpenAiService openAiService;

    List<Trainer> getAllTrainer() {
        return trainerRepository.findAll();
    }

    Trainer getTrainerById(String id) {
        return trainerRepository.findById(id).orElseThrow();
    }

    public Trainer createTrainer(Trainer trainer) {
        return trainerRepository.save(trainer);
    }

    public boolean deleteTrainer(String id) {
        if (trainerRepository.existsById(id)) {
            trainerRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<Trainer> searchTrainer(SearchTrainerRequest parameters) {
        return trainerRepository.findAllByNameContainsOrIdContains(parameters.getId(), parameters.getName());
    }



    public List<ChatThread> getChatThreads(String trainerId) {
        return chatThreadRepository.findAllByTrainerId(trainerId);
    }


    public ChatThread getChatThread(String threadId) {
        return chatThreadRepository.findById(threadId).orElseThrow();
    }

    public ChatThread createChatThread(String trainerId) {
        ChatThread chatThread = new ChatThread();
        chatThread.setTrainer(trainerRepository.findById(trainerId).orElseThrow());

        Thread thread = openAiService.createThread(ThreadRequest.builder().build());
        chatThread.setId(thread.getId());

        return chatThreadRepository.save(chatThread);
    }

    public void deleteChatThread(String id) {
        chatThreadRepository.deleteById(id);
    }

    public Chat getChat(String chatId) {
        return chatRepository.findById(chatId).orElseThrow();
    }

//    public Chat createChat(String threadId,String message){
//        ChatThread thread = getChatThread(threadId);
//        Chat chat = new Chat();
//        chat.setThread(thread);
//
//
//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .model(thread.trainer.model)
//                .prompt(message)
//                .echo(true)
//                .n(1)
//                .maxTokens(25)
//                .logitBias(new HashMap<>())
//                .logprobs(5)
//                .stream(true)
//                .build();
//
//        List<CompletionChoice> choices = openAiService.createCompletion(completionRequest).getChoices();
//
//
//        return chatRepository.save(chat);
//    }

    public Chat createChat(String threadId,String message) {
        ChatThread thread = getChatThread(threadId);
        Chat chat = new Chat();
        chat.setThread(thread);

        CompletionRequest completionRequest = CompletionRequest.builder()
                .model(thread.trainer.model)
                .prompt(message)
                .echo(true)
                .n(1)
                .maxTokens(25)
                .logitBias(new HashMap<>())
                .logprobs(5)
                .stream(true)
                .build();

        CompletionResult completionResult = openAiService.createCompletion(completionRequest);
        StringBuilder sb = new StringBuilder();
        for (CompletionChoice choice : completionResult.getChoices()) {
            sb.append(choice);
        }

        chat.setMessage(sb.toString());
        chat.setMessageId(completionResult.getId());

        return chat;
    }


}
