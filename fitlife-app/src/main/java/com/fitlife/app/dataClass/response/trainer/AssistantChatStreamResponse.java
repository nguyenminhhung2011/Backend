package com.fitlife.app.dataClass.response.trainer;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AssistantChatStreamResponse {
    public UUID threadId;
    public String message;
}
