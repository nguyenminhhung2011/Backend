package com.fitlife.app.repository.jpa.trainer;

import com.fitlife.app.model.trainer.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatJpaRepository extends JpaRepository<Chat, UUID> {
}
