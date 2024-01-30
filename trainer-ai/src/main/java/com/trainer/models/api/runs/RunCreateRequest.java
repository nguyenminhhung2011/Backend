package com.trainer.models.api.runs;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.trainer.models.api.assistants.Tool;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RunCreateRequest {
    @JsonProperty("assistant_id")
    String assistantId;

    // Optional
    String model;
    
    String instructions;
    
    List<Tool> tools;

    Map<String, String> metadata;
}
