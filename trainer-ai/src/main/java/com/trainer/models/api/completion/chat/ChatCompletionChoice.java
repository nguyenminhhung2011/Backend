package com.trainer.models.api.completion.chat;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ChatCompletionChoice {
    private int index;
    @JsonAlias("delta")
    private ChatMessage message;
    @JsonProperty("finish_reason")
    private String finishReason;
}
