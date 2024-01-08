package com.fitlife.app.service.trainer.trainer;

import com.fitlife.app.dataClass.dto.trainer.TrainerDetailDto;
import com.fitlife.app.dataClass.dto.trainer.TrainerDto;
import com.fitlife.app.exceptions.appException.NotFoundException;
import com.fitlife.app.model.trainer.ChatThread;
import com.fitlife.app.model.trainer.Trainer;
import com.fitlife.app.model.user.User;
import com.fitlife.app.repository.jpa.trainer.TrainerJpaRepository;
import com.fitlife.app.repository.jpa.user.UserRepository;
import com.fitlife.app.repository.r2dbc.trainer.TrainerR2dbcRepository;
import com.fitlife.app.utils.mapper.trainer.TrainerMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Service
@AllArgsConstructor
public class TrainerService {
    private final UserRepository userRepository;
    private final TrainerJpaRepository trainerJpaRepository;
    private final TrainerR2dbcRepository trainerR2dbcRepository;
    private final TrainerMapper trainerMapper;

    public Mono<Long> count() {
        return trainerR2dbcRepository.count();
    }

    public Flux<TrainerDto> getTrainerByUser(Long userId) {
        return trainerR2dbcRepository.findAllByUser(userId).map(trainerMapper::toDto);
    }

    public Mono<TrainerDetailDto> getById(UUID trainerId) {
        return trainerR2dbcRepository.findById(trainerId).map(trainerMapper::toDetailDto).doOnError(throwable -> {
            throw new NotFoundException("Trainer not found");
        });
    }

    public Flux<ChatThread> getALlChatThread(UUID trainerId) {
        return trainerR2dbcRepository.findById(trainerId)
                .flatMapIterable(Trainer::getThreads)
                .doOnError(throwable -> {
                    throw new NotFoundException("Trainer not found");
                });
    }

    public TrainerDetailDto createTrainer(Long userId, TrainerDto trainer) {
        return userRepository.findById(userId).map(user -> createTrainer(user, trainer)).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public TrainerDetailDto createTrainer(User user, TrainerDto trainer) {
        return trainerMapper.toDetailDto(trainerJpaRepository.saveAndFlush(Trainer
                .builder()
                .name(trainer.getName())
                .model(trainer.getModel())
                .description(trainer.getDescription())
                .image(trainer.getImage())
                .greetingMessage(trainer.getGreetingMessage())
                .bio(trainer.getBio())
                .user(user)
                .build()
        ));
    }

    public TrainerDto updateTrainer(Long userId, UUID trainerId, TrainerDto trainerDto) {
        if (trainerJpaRepository.existsById(trainerId)) {
            return trainerJpaRepository
                    .findById(trainerId)
                    .map(trainer -> {
                        if (trainer.getUser().getId().equals(userId)) {
                            return trainerMapper.toDto(trainerJpaRepository.save(Trainer
                                    .builder()
                                    .id(trainer.getId())
                                    .name(trainerDto.getName())
                                    .model(trainerDto.getModel())
                                    .description(trainerDto.getDescription())
                                    .image(trainerDto.getImage())
                                    .greetingMessage(trainerDto.getGreetingMessage())
                                    .bio(trainerDto.getBio())
                                    .user(trainer.getUser())
                                    .threads(trainer.getThreads())
                                    .build()));
                        } else {
                            throw new RuntimeException("User not authorized");
                        }
                    })
                    .orElseThrow(() -> new RuntimeException("Trainer not found"));
        } else {
            throw new RuntimeException("Trainer not found");
        }
    }

    public void deleteTrainer(Long userId, UUID trainerId) {
        trainerJpaRepository
                .findById(trainerId)
                .map(trainer -> {
                    if (trainer.getUser().getId().equals(userId)) {
                        trainerJpaRepository.deleteById(trainerId);
                        return trainerMapper.toDto(trainer);
                    } else {
                        throw new RuntimeException("User not authorized");
                    }
                })
                .orElseThrow(() -> new RuntimeException("Trainer not found"));
    }
}
