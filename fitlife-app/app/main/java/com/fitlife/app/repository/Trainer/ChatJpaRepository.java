package com.fitlife.app.Repository.Trainer;

import com.fitlife.app.Model.Trainer.Chat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChatJpaRepository extends JpaRepository<Chat, UUID> {
}
