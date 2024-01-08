package com.fitlife.app.repository.Trainer;

import com.fitlife.app.model.Trainer.ChatThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatThreadJpaRepository extends JpaRepository<ChatThread, UUID> {

}
