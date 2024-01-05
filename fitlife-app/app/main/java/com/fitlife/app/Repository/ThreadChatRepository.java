package com.fitlife.app.Repository;

import com.fitlife.app.Model.Trainer.ChatThread;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ThreadChatRepository extends JpaRepository<ChatThread, Long> {
}
