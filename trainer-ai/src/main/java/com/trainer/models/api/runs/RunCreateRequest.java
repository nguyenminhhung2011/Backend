package com.trainer.models.api.runs;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.catalina.startup.Tool;

import java.util.List;
import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class RunCreateRequest {
    String assistantId;

    // Optional
    String model;
    
    String instructions;
    
    List<Tool> tools;

    Map<String, String> metadata;
}
