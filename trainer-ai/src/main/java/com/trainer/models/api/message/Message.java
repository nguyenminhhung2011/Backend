package com.trainer.models.api.message;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

import java.util.List;
import java.util.Map;

@Builder
public record Message (
    String id,

    String object,

    @JsonProperty("created_at")
    int createdAt,

    @JsonProperty("thread_id")
    String threadId,

    String role,

    List<MessageContent> content,

    @JsonProperty("assistant_id")
    String assistantId,

    @JsonProperty("run_id")
    String runId,

    @JsonProperty("file_ids")
    List<String> fileIds,
    Map<String, String> metadata
){}