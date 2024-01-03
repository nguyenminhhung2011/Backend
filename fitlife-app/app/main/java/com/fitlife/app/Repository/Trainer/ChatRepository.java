package com.fitlife.app.Repository.Trainer;

import com.fitlife.app.Model.Trainer.Chat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRepository extends JpaRepository<Chat, String> {
}
